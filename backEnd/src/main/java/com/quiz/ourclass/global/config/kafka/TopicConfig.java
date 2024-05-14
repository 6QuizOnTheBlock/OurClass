package com.quiz.ourclass.global.config.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;


/* 토픽을 컴파일 시간에 생성하는 Config
 카프카를 들여다 보고, 이미 같은 이름으로 토픽이 존재하면, 파티션 수만 동적으로 업데이트 가능, 나머지는 변경 불가.
 만약 카프카를 동적으로, 업데이트하고 싶다면, 같은 이름의 Topic을 삭제하고 재 생성 해야한다.
 */
@Configuration
public class TopicConfig {

    @Value("${kafka.url}")
    String kafkaUrl;

    /*
     * kafkaAdmin: 새 토픽을 생성하는 객체
     * 서버 컴파일 시, [NewTopic]이란 객체로 토픽을 새로 만든다.
     *               기존에 이미 존재한다면, 파티션 수만 업데이트 한다.
     * */

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);

        return new KafkaAdmin((configs));
    }

    /*
     *  [Kafka]에 대한 조회 및 삭제를 담당하는 객체
     *  [KafkaUtil]에서 사용
     * */

    @Bean
    public AdminClient adminClient(KafkaAdmin kafkaAdmin) {
        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }


    /* partitions: 병렬처리할 파티션 수 -> 많을수록 처리속도 UP, 동시성 제어 down -> [Consumer]에서 직렬화 처리 필요
       replica: 복제본의 수 -> 주로 쓰고 있는 leader Broker가 다운되었을 때, 대타로 쓰임
                현업에서는 기본 3개가 권장된다고 한다.
                이 경우, leader follower 구조에 따라 죽은 [Leader]를 대신해, 다른 [Follower]가 리더로 선정
    */
    @Bean
    public NewTopic gamer() {
        return TopicBuilder.name("gamer")
            .partitions(4)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic question() {
        return TopicBuilder.name("question")
            .partitions(1)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic answer() {
        return TopicBuilder.name("answer")
            .partitions(1)
            .replicas(1)
            .build();
    }


}
