package com.quiz.ourclass.global.util.scheduler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@RequiredArgsConstructor
@Service
public class SchedulingService {

    private final TaskScheduler taskScheduler;
    private final TransactionTemplate transactionTemplate;

    public <T> void scheduleTask(T target, Consumer<T> action, LocalDateTime executionTime) {
        SchedulingTask<T> task = new SchedulingTask<>(target, action, transactionTemplate);
        Instant executionDate = executionTime.atZone(ZoneId.systemDefault()).toInstant();

        taskScheduler.schedule(task, executionDate);
    }
}