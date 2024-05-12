package com.quiz.ourclass.domain.notice.service;

import com.quiz.ourclass.domain.notice.dto.SseDTO;
import com.quiz.ourclass.domain.notice.dto.SseType;
import com.quiz.ourclass.domain.notice.repository.SseRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.UserAccessUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Service
public class SseServiceImpl implements SseService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 5;
    private static final Long REDIRECT_TIME = 5L * 1000;
    private final SseRepository sseRepository;
    private final UserAccessUtil accessUtil;

    @Override
    public SseEmitter subscribe(String lastEventId) {
        long loginUserId = accessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)).getId();
        String emitterId = makeTimeIncludeId(String.valueOf(loginUserId));
        SseEmitter emitter = sseRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // emitter의 상태를 체크함, 완료되었는지 타임아웃이 났는지
        checkEmitterStatus(emitter, emitterId);

        // 503 에러 방지 더미 이벤트 전송
        SseDTO sseDTO = SseDTO.builder()
            .eventType(SseType.SSE_CONNECT)
            .receiverId(loginUserId)
            .url(null)
            .time(LocalDateTime.now())
            .build();
        sendSse(emitter, emitterId, emitterId, sseDTO);

        // 클라이언트가 미수신한 Event 전송
        if (lastEventId != null && !lastEventId.isEmpty()) {
            sendLostData(lastEventId, String.valueOf(loginUserId), emitterId, emitter);
        }

        return emitter;
    }

    @Override
    //특정 유저에게 알림 전송
    public void send(SseDTO sseDTO) {

        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = sseRepository.findAllEmitterStartWithByUserId(
            String.valueOf(sseDTO.receiverId()));

        sseEmitters.forEach(
            (key, emitter) -> {
                // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                sseRepository.saveEventCache(key, sseDTO);
                // 데이터 전송
                sendSse(emitter, makeTimeIncludeId(String.valueOf(sseDTO.receiverId())), key,
                    sseDTO);
            }
        );
    }

    private void sendSse(SseEmitter emitter, String eventId, String emitterId, SseDTO dto) {

        try {
            emitter.send(SseEmitter.event()
                .id(eventId)
                .reconnectTime(REDIRECT_TIME)
                .name(dto.eventType().toString())
                .data(dto));
        } catch (IOException exception) {
            sseRepository.deleteById(emitterId);
            emitter.completeWithError(exception);
        }
    }

    private String makeTimeIncludeId(String userId) {
        return userId + "_" + System.currentTimeMillis();
    }

    //종료 상태
    private void checkEmitterStatus(SseEmitter emitter, String emitterId) {
        emitter.onCompletion(() -> {
            sseRepository.deleteById(emitterId);
        });
        emitter.onTimeout(() -> {
            sseRepository.deleteById(emitterId);
        });
        emitter.onError((e) -> sseRepository.deleteById(emitterId));
    }

    private void sendLostData(String lastEventId, String userId, String emitterId,
        SseEmitter emitter) {
        Map<String, SseDTO> eventCaches = sseRepository.findAllEventCacheStartWithByUserId(
            userId);
        eventCaches.forEach((eventId, event) -> {
            if (lastEventId.compareTo(eventId) < 0) {
                sendSse(emitter, eventId, emitterId, event);
            }
        });
    }
}
