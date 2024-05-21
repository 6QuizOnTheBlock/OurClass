package com.quiz.ourclass.global.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 타겟 단위 -> 메소드 단위에만 어노테이션을 붙일 수 있도록
@Retention(RetentionPolicy.RUNTIME) // 어노테이션이 언제까지 유효할지 설정 -> 실행 기간동안 유지 되어야 함.
public @interface LogInclude {
    // Log 찍고 싶은 매소드에게 붙이는 어노테이션
    // [LogInclude]라는 어노테이션을 붙이면, 로그가 찍힘.
}
