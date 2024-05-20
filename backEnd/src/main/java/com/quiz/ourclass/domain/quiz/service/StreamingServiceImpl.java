package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.domain.quiz.dto.request.AnswerRequest;
import com.quiz.ourclass.domain.quiz.dto.request.QuestionRequest;
import com.quiz.ourclass.domain.quiz.dto.response.AnswerResponse;
import com.quiz.ourclass.domain.quiz.entity.Quiz;
import com.quiz.ourclass.domain.quiz.repository.jpa.QuizRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamingServiceImpl implements StreamingService {

    private final QuizSend quizSend;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final QuizRepository quizRepository;


    public void sendGamer(GamerDTO gamer) {
        try {
            quizSend.sendNewGamer(gamer);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.FAILED_TO_SENDING_MESSAGE);
        }
    }

    public void sendQuestion(QuestionRequest request) {
        try {
            quizSend.sendQuestion(request);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.FAILED_TO_SENDING_MESSAGE);
        }
    }

    public void sendAnswer(AnswerRequest request, String accessToken) {
        Claims info = jwtUtil.getUserInfoFromToken(accessToken);
        log.info(info.getSubject());
        long id = Long.parseLong(info.getSubject());
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MEMBER));
        Quiz quiz = quizRepository.findById(request.quizId())
            .orElseThrow(() -> new GlobalException(ErrorCode.NO_QUIZ_GAME));

        AnswerResponse answerResponse = new AnswerResponse(request.quizGameId(), request.quizId(),
            member.getId(),
            member.getName(), member.getProfileImage(), request.answer(), quiz.getAnswer());

        try {
            quizSend.sendAnswer(answerResponse);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.FAILED_TO_SENDING_MESSAGE);
        }
    }
}
