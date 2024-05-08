package com.quiz.ourclass.domain.chat.interceptor;

import com.quiz.ourclass.domain.chat.service.ChatRoomServiceImpl;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

// 우선 순위를 높게 설정해서 SecurityFilter 보다 앞서 실행되게 해준다.
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@RequiredArgsConstructor
@Slf4j
public class ChatHandler implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ChatRoomServiceImpl chatRoomServiceImpl;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // StompCommand 따라서 로직을 분기해서 처리하는 메서드를 호출한다.
        String memberId = verifyAccessToken(getAccessToken(accessor));
        log.info("StompAccessor = {}", accessor);
        handleMessage(accessor.getCommand(), accessor, memberId);
        return message;
    }

    private void handleMessage(
        StompCommand stompCommand, StompHeaderAccessor accessor, String memberId
    ) {
        switch (stompCommand) {
            case CONNECT:
                connectToChatRoom(accessor, memberId);
                break;
            case SUBSCRIBE:
            case SEND:
                verifyAccessToken(getAccessToken(accessor));
                break;
        }
    }

    private String getAccessToken(StompHeaderAccessor accessor) {
        return accessor.getFirstNativeHeader("Authorization");
    }

    private void connectToChatRoom(StompHeaderAccessor accessor, String memberId) {
        // 채팅방 번호를 가져온다.
        Long roomId = getChatRoomId(accessor);

        //멤버가 단체에 속해있는지 확인 (채팅방 접근 권한)
        chatRoomServiceImpl.isMemberAuthorizedToJoinRoom(Long.valueOf(memberId), roomId);

        // Redis 통해 채팅방 입장 처리
        redisUtil.addChatRoomUser(roomId, memberId);
    }

    private String verifyAccessToken(String accessToken) {
        if (jwtUtil.validateToken(accessToken) == -1) {
            throw new IllegalStateException("토큰이 만료되었습니다.");
        }
        return jwtUtil.getUserInfoFromToken(accessToken).getSubject();
    }

    private Long getChatRoomId(StompHeaderAccessor accessor) {
        return Long.valueOf(
            Objects.requireNonNull(
                accessor.getFirstNativeHeader("roomId")
            )
        );
    }
}
