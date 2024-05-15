package com.quiz.ourclass.domain.quiz.service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class CountdownService {

    private LocalDateTime countDownStart;
    private final int countdownDurationSeconds = 60; // 대기방에서 최대 대기시간 60초 설정

    public void startCountDown() {
        this.countDownStart = LocalDateTime.now();
    }

    public long getCurrentCountDown() {
        if (countDownStart == null) {
            return countdownDurationSeconds; // 시작 안 했으면 60초 그대로 주기
        }
        Duration duration = Duration.between(countDownStart, LocalDateTime.now());
        long secondsElapsed = duration.getSeconds(); // 경과 시간
        return (countdownDurationSeconds - secondsElapsed); // 60초 - 시작 시간으로부터 경과 초
        // -이면 이미 게임은 시작했다라는 뜻.
    }
}
