package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.global.util.ConstantUtil;
import com.quiz.ourclass.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuizReceive {

    private final SimpMessageSendingOperations template;
    private final RedisUtil redisUtil;

    @KafkaListener(topics = ConstantUtil.QUIZ_GAMER, containerFactory = "kafkaListenerContainerFactory")
    public void receivedGamer(GamerDTO gamer) {
        log.info("/gamer/" + gamer.quizGameId());
        log.info("게이머 상세: 이름={}.포인트={}", gamer.name(), gamer.point());

        // 1. redis 에 해당 회원이 있는지 확인
        if (redisUtil.getMemberScore(gamer.quizGameId(), gamer.id()) == null) {
            redisUtil.setMemberScore(gamer.quizGameId(), gamer.id(), gamer.point());
        }
        // 2. 없으면 신규로 Zset에 넣기
        // 3. 있으면, 넘어가기

        template.convertAndSend("/gamer/" + gamer.quizGameId(),
            redisUtil.getAllMemberScores(gamer.quizGameId()));
    }

}
