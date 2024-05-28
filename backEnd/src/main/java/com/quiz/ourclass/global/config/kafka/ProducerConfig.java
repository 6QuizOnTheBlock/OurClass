package com.quiz.ourclass.global.config.kafka;

import com.google.common.collect.ImmutableMap;
import com.quiz.ourclass.domain.chat.dto.Message;
import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.domain.quiz.dto.request.QuestionRequest;
import com.quiz.ourclass.domain.quiz.dto.response.AnswerResponse;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class ProducerConfig {

    @Value("${kafka.url}")
    String kafkaUrl;

    // Kafka Producer 구성을 위한 설정값들을 포함한 맵을 반환하는 메서드
    @Bean
    public Map<String, Object> producerConfigurations() {
        return ImmutableMap.<String, Object>builder()
            .put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaUrl)
            .put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class)
            .put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class)
            .build();
    }


    // Message ProducerFactory 생성하는 Bean 메서드
    @Bean
    public ProducerFactory<String, Message> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }

    // [Message template]를 생성하는 Bean 메서드
    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    // Gamer Template -> 총 게이머에 대한 명세(이름, id, 프로필 사진), 게이머의 랭킹
    @Bean
    public ProducerFactory<String, GamerDTO> gamerProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }

    @Bean
    public KafkaTemplate<String, GamerDTO> gamerTemplate() {
        return new KafkaTemplate<>(gamerProducerFactory());
    }

    @Bean
    public ProducerFactory<String, QuestionRequest> questionProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }

    @Bean
    public KafkaTemplate<String, QuestionRequest> questionTemplate() {
        return new KafkaTemplate<>(questionProducerFactory());
    }

    @Bean
    public ProducerFactory<String, AnswerResponse> answerProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }

    @Bean
    public KafkaTemplate<String, AnswerResponse> answerTemplate() {
        return new KafkaTemplate<>(answerProducerFactory());
    }
}
