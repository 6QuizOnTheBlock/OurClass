package com.quiz.ourclass.domain.quiz.controller;

import com.quiz.ourclass.domain.quiz.dto.gamerDTO;
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

    // 1. gamer : [gamer List]를 point 가 높은 순으로 주는 TOPIC
    @MessageMapping("/gamer")
    public void getGamer(gamerDTO message, @Header("Authorization") final String accessToken) {

    }

    // 2. answer -> 게이머들의 답변이 들어 오는 TOPIC -> point 재집계,
    @MessageMapping("/answer")
    public void sendAnwer(final String message, @Header("Authorization") final String accessToken) {

    }
}
