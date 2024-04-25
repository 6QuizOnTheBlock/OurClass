package com.quiz.ourclass.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus status;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.errorCode = errorCode;
    }
}