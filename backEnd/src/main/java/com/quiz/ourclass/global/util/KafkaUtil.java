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
        } catch (InterruptedException | ExecutionException e) {
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
