package com.quiz.ourclass.domain.chat.service.message;

import com.quiz.ourclass.domain.chat.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSend {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    // 메시지를 지정한 Kafka 토픽으로 전송
    public void send(String topic, Message data) {
        // KafkaTemplate 사용하여 메시지를 지정된 토픽으로 전송
        kafkaTemplate.send(topic, data);
    }
}
