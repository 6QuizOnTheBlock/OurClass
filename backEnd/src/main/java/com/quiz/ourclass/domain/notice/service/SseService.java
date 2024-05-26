package com.quiz.ourclass.domain.notice.service;

import com.quiz.ourclass.domain.notice.dto.SseDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {

    SseEmitter subscribe(String lastEventId);

    void send(SseDTO sseDto);
}
