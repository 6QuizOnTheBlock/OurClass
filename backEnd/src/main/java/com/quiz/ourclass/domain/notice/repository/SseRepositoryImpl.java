package com.quiz.ourclass.domain.notice.repository;

import com.quiz.ourclass.domain.notice.dto.SseDTO;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
@RequiredArgsConstructor
public class SseRepositoryImpl implements SseRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, SseDTO> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {

        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String eventId, SseDTO event) {

        eventCache.put(eventId, event);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId) {
        return emitters.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(userId))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, SseDTO> findAllEventCacheStartWithByUserId(String userId) {
        return eventCache.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(userId))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String id) {

        emitters.remove(id);
    }
}