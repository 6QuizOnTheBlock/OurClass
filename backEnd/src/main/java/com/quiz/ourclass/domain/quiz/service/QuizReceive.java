package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.domain.quiz.dto.request.QuestionRequest;
import com.quiz.ourclass.domain.quiz.dto.response.AnswerResponse;
import com.quiz.ourclass.domain.quiz.repository.jpa.QuizRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.ConstantUtil;
import com.quiz.ourclass.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuizReceive {

    private final SimpMessageSendingOperations template;
    private final QuizRepository quizRepository;
    private final RedisUtil redisUtil;
    @Value("${kafka.group}")
    String kafkaGroup;

    @KafkaListener(topics = ConstantUtil.QUIZ_GAMER, containerFactory = "kafkaListenerContainerFactory", concurrency = "4")
    public void receivedGamer(GamerDTO gamer) {
        log.info("/gamer/" + gamer.quizGameId());
        log.info("게이머 상세={}", gamer.toString());

        switch (gamer.gamerStatus()) {
            case ENTER:
                // [Redis]에서 멤버를 확인하고 없으면 신규 추가
                if (redisUtil.getMemberScore(gamer.id(), gamer.quizGameId()) == null) {
                    redisUtil.setMemberScore(gamer.id(), gamer.quizGameId(), gamer.point());
                }
                break;
            case LEAVE:
                redisUtil.removeMemberScore(gamer.id(), gamer.quizGameId());
                break;
            default:
                log.warn("알 수 없는 게이머 상태: {}", gamer.gamerStatus());
                throw new GlobalException(ErrorCode.UNKNOWN_GAMER_STATUS);
        }

        template.convertAndSend("/subscribe/gamer/" + gamer.quizGameId(),
            quizRepository.getRankingList
                (gamer.quizGameId(), redisUtil.getAllMemberScores(gamer.quizGameId())));
    }

    @KafkaListener(topics = ConstantUtil.QUIZ_QUESTION, containerFactory = "kafkaListenerContainerFactory")
    public void receivedQuestion(QuestionRequest request) {
        log.info("보내줘야 할 질문 상세={}", request.toString());

        template.convertAndSend("/subscribe/question/" + request.quizGameId(),
            quizRepository.getQuiz(
                request.quizGameId(), request.id()));
    }

    @KafkaListener(topics = ConstantUtil.QUIZ_ANSWER, containerFactory = "kafkaListenerContainerFactory")
    public void receivedAnswer(AnswerResponse response) {
        log.info("보내줘야할 답 상세={}", response.toString());

        if (response.submit().replaceAll(" ", "").equals(response.ans().replaceAll(" ", ""))) {

            log.info(String.valueOf(
                response.submit().replaceAll(" ", "").equals(response.ans().replaceAll(" ", ""))));

            int score =
                redisUtil
                    .getMemberScore(response.studentId(), response.quizGameId()).intValue() + 100;
            redisUtil.setMemberScore(response.studentId(), response.quizGameId(), score);
        }

        template.convertAndSend("/subscribe/answer/" + response.quizGameId(), response);
    }

}
