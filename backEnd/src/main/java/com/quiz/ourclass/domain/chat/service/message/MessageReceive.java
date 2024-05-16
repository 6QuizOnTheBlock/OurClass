package com.quiz.ourclass.domain.chat.service.message;

import com.quiz.ourclass.domain.chat.dto.Message;
import com.quiz.ourclass.domain.chat.repository.ChatRepository;
import com.quiz.ourclass.global.util.ConstantUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReceive {

    private final SimpMessageSendingOperations template;
    private final ChatRepository chatRepository;

    @KafkaListener(topics = ConstantUtil.KAFKA_TOPIC, containerFactory = "chatContainerFactory")
    public void receiveMessage(Message message) {
        log.info("전송 위치 = /subscribe/public/" + message.getRoomId());
        log.info("채팅 방으로 메시지 전송 = {}", message.getContent());

        chatRepository.save(message.convertEntity());

        // 메시지객체 내부의 채팅방번호를 참조하여, 해당 채팅방 구독자에게 메시지를 발송한다.
        template.convertAndSend("/subscribe/public/" + message.getRoomId(), message);
    }
}
