package com.quiz.ourclass.domain.quiz.service;

import com.quiz.ourclass.domain.quiz.dto.GamerDTO;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuizSend {

    private final KafkaTemplate<String, GamerDTO> gamerTemplate;
    private final RetryTemplate retryTemplate;

    // 1) 게이머를 Kafka 게이머 토픽으로 전송
    public void sendNewGamer(GamerDTO gamer) throws Exception {

        retryTemplate.execute((RetryCallback<Void, Exception>) context -> {

            CompletableFuture<SendResult<String, GamerDTO>> future = gamerTemplate.send("gamer",
                gamer);

            future.whenComplete((result, e) -> {
                if (e != null) {
                    log.info("메세지를 보내는데 실패했습니다. 보내지 못한 메세지: {}, due to : {} ", gamer,
                        e.getMessage());
                    throw new GlobalException(ErrorCode.FAILED_TO_SENDING_MESSAGE);
                } else {
                    log.info("메세지 전송 성공: {}", gamer);
                }
            });

            try {
                // get()을 호출하여 비동기 작업이 완료될 때까지 대기
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new GlobalException(ErrorCode.FAILED_TO_SENDING_MESSAGE);
            }
            return null;
        });

        gamerTemplate.send("gamer", gamer);
    }


}
