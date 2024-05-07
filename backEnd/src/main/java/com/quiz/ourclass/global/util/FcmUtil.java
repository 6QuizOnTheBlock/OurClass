package com.quiz.ourclass.global.util;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.global.dto.FcmDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class FcmUtil {

    private final RedisUtil redisUtil;
    private static final String FCM_KEY_PREFIX = "FCM_";
    private static final int MAX_RETRIES = 5; //최대 재시도 횟수
    private static final long INITIAL_BACKOFF = 1000L; //초기 백오프 시간 (1초)

    @Async("taskExecutor")
    public void singleFcmSend(Member member, FcmDTO fcmDTO) {
        String fcmRedisKey = getFcmRedisKey(member.getId());
        String fcmToken = redisUtil.valueGet(fcmRedisKey);

        if (fcmToken != null && !fcmToken.isEmpty()) {
            Message message = makeMessage(fcmDTO.getTitle(), fcmDTO.getBody(), fcmToken);
            sendMessage(message);
        }
    }

    public void multiFcmSend(List<Member> members, FcmDTO fcmDTO) {
        members.forEach(member -> singleFcmSend(member, fcmDTO));
    }

    private String getFcmRedisKey(Long memberId) {
        return FCM_KEY_PREFIX + memberId;
    }

    public Message makeMessage(String title, String body, String token) {
        Notification.Builder notificationBuilder =
            Notification.builder()
                .setTitle(title)
                .setBody(body);

        return Message.builder()
            .setNotification(notificationBuilder.build())
            .setToken(token)
            .build();
    }

    private void sendMessage(Message message) {
        int attempt = 0;
        long backoff = INITIAL_BACKOFF;

        while (attempt < MAX_RETRIES) { //지수 백오프 전략
            try {
                FirebaseMessaging.getInstance().send(message);
                log.info("FCM Send Success");
                break;  // 성공 시 루프 종료
            } catch (FirebaseMessagingException e) {
                log.error("FCM Send Error: {}", e.getMessage());
                attempt++;
                if (attempt >= MAX_RETRIES) {
                    // 최대 재시도 횟수 도달 시 루프 종료
                    // 다른 메시지 시스템으로 알림을 전송하는 방법을 고려해볼 수 있음
                    log.error("Reached Maximum Retry Attempts");
                    break;
                }
                try {
                    Thread.sleep(backoff);  // 지수 백오프를 위한 대기
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.error("Interrupted While Waiting For Retry");
                    break;  // 인터럽트 발생 시 루프 종료
                }
                backoff *= 2;
            }
        }
    }

    public FcmDTO makeFcmDTO(String title, String body) {
        return FcmDTO.builder()
            .title(title)
            .body(body)
            .build();
    }

    public String makeReportTitle(String organizationName, String type) {
        return organizationName + " " + type + " 신고";
    }

    public String makeReportBody(String authorMember, String reportMember, String type) {
        return authorMember + " 학생이 작성한 " + type + "을" + reportMember + "학생이 신고하였습니다.";
    }
}