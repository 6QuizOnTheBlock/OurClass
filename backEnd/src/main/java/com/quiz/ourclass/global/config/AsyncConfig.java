package com.quiz.ourclass.global.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 코어 쓰레드 풀 사이즈
        executor.setMaxPoolSize(10); // 최대 쓰레드 풀 사이즈
        executor.setQueueCapacity(100); // 큐 용량
        executor.setThreadNamePrefix("Async-Executor-");
        executor.initialize();
        return new DelegatingSecurityContextExecutor(executor);
    }
}
