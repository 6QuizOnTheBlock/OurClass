package com.quiz.ourclass.global.config.kafka;

import com.quiz.ourclass.domain.chat.interceptor.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // WebSocket 을 활성화하고 메시지 브로커 사용가능
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ChatHandler chatHandler;

    // STOMP 엔드포인트를 등록하는 메서드
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp") // STOMP 엔드포인트 설정
            .setAllowedOriginPatterns("*") // 모든 Origin 허용 -> 배포시에는 보안을 위해 Origin 을 정확히 지정
            .withSockJS(); // SockJS 사용가능 설정
    }

    // 메시지 브로커를 구성하는 메서드
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // /subscribe/{roomId}로 주제 구독 가능
        registry.enableSimpleBroker("/subscribe", "/user");
        // /publish/message 로 메시지 전송 컨트롤러 라우팅 가능
        registry.setApplicationDestinationPrefixes("/publish");
        // 요청을 보낸 사용자에게 바로 전송할 때 사용자 식별을 위한 접두사 세팅
        registry.setUserDestinationPrefix("/user");
    }

    // 클라이언트 인바운드 채널을 구성하는 메서드
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // chatHandler 를 인터셉터로 등록하여 STOMP 메시지 핸들링을 수행
        registration.interceptors(chatHandler);
    }

    // STOMP 에서 64KB 이상의 데이터 전송하기 위한 설정
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(160 * 64 * 1024);
        registry.setSendTimeLimit(100 * 10000);
        registry.setSendBufferSizeLimit(3 * 512 * 1024);
    }

}
