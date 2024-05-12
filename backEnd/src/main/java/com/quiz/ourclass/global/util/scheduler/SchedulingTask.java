package com.quiz.ourclass.global.util.scheduler;

import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import org.springframework.transaction.support.TransactionTemplate;

@AllArgsConstructor
public class SchedulingTask<T> implements Runnable {

    private final T target;
    private final Consumer<T> task;
    private final TransactionTemplate transactionTemplate;

    @Override
    public void run() {
        transactionTemplate.execute(status -> {
            task.accept(target);
            return null;
        });
    }
}
