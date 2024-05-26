package com.quiz.ourclass.global.util;

import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaUtil {

    private final AdminClient adminClient;

    public void getTopicMeta(String topic) {
        DescribeTopicsResult describeTopicsResult = adminClient
            .describeTopics(Collections.singleton(topic));

        KafkaFuture<Map<String, TopicDescription>> future = describeTopicsResult.allTopicNames();

        try {
            Map<String, TopicDescription> descriptions = future.get();
            descriptions.forEach((name, description) -> {
                log.info("Topic: {} \nPartitions: {}", name, description.partitions().size());
            });
            // 현재 쓰레드가 인터럽트 되면, 발생하는 예외
            // 인터럽트 되었다 => 다른 쓰레드가 현재 쓰레드에게 작업을 중단하고 가능한 빨리 종료하라는 신호를 보냈다.
            // 지금은 InterruptedException 예외를 잡아서 서버 자체 예외만 던지고, 쓰레드 처리에 대한 조치를 하지 않고 있다.
            // 이렇게 되면, 쓰레드가 정상 종료되지 않아 문제가 된다.
            // 따라서 정상 종료 절차를 수행하고, 원래대로 우리 식의 예외를 수행해야 한다.
        } catch (InterruptedException e) {
            // InterruptedException 이 발생하면, 현재 쓰레드를 다시 인터럽트 시킨다.
            // InterruptedException 으로 인터럽트를 캐치하면, 현재 쓰레드가 인터럽트 되었다는 정보를 잃기 때문이다.
            Thread.currentThread().interrupt();
            throw new GlobalException(ErrorCode.CANT_LOAD_KAFKA);
        } catch (ExecutionException e) {
            throw new GlobalException(ErrorCode.CANT_LOAD_KAFKA);
        }
    }

    public void deleteTopic(String topicName) {
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(
            Collections.singletonList(topicName));
        KafkaFuture<Void> future = deleteTopicsResult.all();

        try {
            future.get();
            log.info("Topic {} 가 성공적으로 삭제 되었습니다.", topicName);
        } catch (InterruptedException | ExecutionException e) {
            throw new GlobalException(ErrorCode.CANT_LOAD_KAFKA);
        }
    }
}
