package com.quiz.ourclass.global.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExclusion {
    // Log 찍기 싫은 매소드에게 붙이는 어노테이션
    // 해당 어노테이션을 붙이면 로그가 찍히지 않는다.
}
