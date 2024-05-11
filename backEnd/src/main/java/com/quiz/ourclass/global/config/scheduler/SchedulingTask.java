package com.quiz.ourclass.global.config.scheduler;

import java.util.function.Consumer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SchedulingTask<T> implements Runnable {

    private final T target;
    private final Consumer<T> task;

    @Override
    public void run() {
        task.accept(target);
    }
}
