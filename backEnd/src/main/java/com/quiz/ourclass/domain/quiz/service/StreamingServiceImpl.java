package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreamingServiceImpl implements StreamingService {

    private final QuizSend quizSend;

    public void sendGamer(GamerDTO gamer) {
        try {
            quizSend.sendNewGamer(gamer);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.FAILED_TO_SENDING_MESSAGE);
        }
    }
}
