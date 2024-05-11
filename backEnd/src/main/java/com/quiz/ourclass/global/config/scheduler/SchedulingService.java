package com.quiz.ourclass.global.config.scheduler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchedulingService {

    private final TaskScheduler taskScheduler;

    public <T> void scheduleTask(T target, Consumer<T> action, LocalDateTime executionTime) {
        SchedulingTask<T> task = new SchedulingTask<>(target, action);
        Instant executionDate = executionTime.atZone(ZoneId.systemDefault()).toInstant();

        taskScheduler.schedule(task, executionDate);
    }
}