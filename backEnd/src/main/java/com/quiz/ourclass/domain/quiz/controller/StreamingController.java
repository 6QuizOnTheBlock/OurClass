package com.quiz.ourclass.domain.quiz.controller;

import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.domain.quiz.dto.request.AnswerRequest;
import com.quiz.ourclass.domain.quiz.dto.request.QuestionRequest;
import com.quiz.ourclass.domain.quiz.service.CountdownService;
import com.quiz.ourclass.domain.quiz.service.StreamingServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/quiz")
public class StreamingController {

    private final StreamingServiceImpl streamingService;
    private final CountdownService countdownService;
    // 1. gamer : [gamer List]를 point 가 높은 순으로 주는 TOPIC

    @MessageMapping("/gamer")
    public void getGamer(GamerDTO message) {
        log.info(message.toString());
        streamingService.sendGamer(message);
    }

    // 2. 질문 요청에 대한 응답
    @MessageMapping("/question")
    public void sendQuestion(QuestionRequest request) {
        streamingService.sendQuestion(request);
    }

    // 3. answer -> 게이머들의 답변이 들어 오는 TOPIC -> point 재집계,
    @MessageMapping("/answer")
    public void sendAnswer(final AnswerRequest request,
        @Header("Authorization") final String accessToken) {
        streamingService.sendAnswer(request, accessToken);
    }

}
