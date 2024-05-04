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
        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("FCM send error", e);
        }
    }
}
