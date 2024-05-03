package com.quiz.ourclass.domain.notice.repository;

import com.quiz.ourclass.domain.notice.dto.SseDTO;
import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    void saveEventCache(String eventId, SseDTO event);

    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId);

    Map<String, SseDTO> findAllEventCacheStartWithByUserId(String userId);

    void deleteById(String id);
}
