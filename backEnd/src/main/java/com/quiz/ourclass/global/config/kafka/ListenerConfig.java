package com.quiz.ourclass.global.config.kafka;

import com.google.common.collect.ImmutableMap;
import com.quiz.ourclass.domain.chat.dto.Message;
import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.domain.quiz.dto.request.QuestionRequest;
import com.quiz.ourclass.domain.quiz.dto.response.AnswerResponse;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class ListenerConfig {

    @Value("${kafka.url}")
    String kafkaUrl;

    @Value("${kafka.group}")
    String kafkaGroup;

    // KafkaListener 컨테이너 팩토리를 생성하는 Bean 메서드
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // Kafka ConsumerFactory 생성하는 Bean 메서드
    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        JsonDeserializer<Message> deserializer = new JsonDeserializer<>();
        // 패키지 신뢰 오류로 인해 모든 패키지를 신뢰하도록 작성
        deserializer.addTrustedPackages("*");

        // Kafka Consumer 구성을 위한 설정값들을 설정 -> 변하지 않는 값이므로 ImmutableMap 을 이용하여 설정
        Map<String, Object> consumerConfigurations =
            ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl)
                .put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroup)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                .build();

        return new DefaultKafkaConsumerFactory<>(
            consumerConfigurations, new StringDeserializer(), deserializer
        );
    }

    // Kafka Consumer 설정
    @Bean
    public Map<String, Object> consumerConfigurations() {
        return ImmutableMap.<String, Object>builder()
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaUrl)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, kafkaGroup)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest")
            .build();
    }

    // ConsumerFactory 설정
    @Bean
    public ConsumerFactory<String, GamerDTO> gamerConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
            consumerConfigurations(),
            new StringDeserializer(),
            new JsonDeserializer<>(GamerDTO.class));
    }

    // Listener Container 설정
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GamerDTO> gamerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GamerDTO> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(gamerConsumerFactory());
        return factory;
    }

    // Question Consumer 설정
    @Bean
    public ConsumerFactory<String, QuestionRequest> questionConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
            consumerConfigurations(),
            new StringDeserializer(),
            new JsonDeserializer<>(QuestionRequest.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, QuestionRequest> questionKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, QuestionRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(questionConsumerFactory());
        return factory;
    }

    // Answer Consumer 설정
    @Bean
    public ConsumerFactory<String, AnswerResponse> answerConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
            consumerConfigurations(),
            new StringDeserializer(),
            new JsonDeserializer<>(AnswerResponse.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AnswerResponse> answerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AnswerResponse> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(answerConsumerFactory());
        return factory;
    }


}
