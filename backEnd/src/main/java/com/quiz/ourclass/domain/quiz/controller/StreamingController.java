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
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
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

    // 3. 서버 <-> 클라이언트 시간 동기화 (React 화면 마운트 시에 서버에 요청, 서버는 남은 카운트 다운 수 전송)
    @MessageMapping("/current")
    @SendToUser("/queue/time") // 이 어노테이션은 요청을 보낸 클라이언트의 해당 주소로만 값을 보냄!
    public String currentTime(StompHeaderAccessor sha) {
        log.info("여기까지 왔다!={}", sha.getSessionId());
        log.info("Destination={}", sha.getDestination());
        return String.valueOf(countdownService.getCurrentCountDown());
    }


}
