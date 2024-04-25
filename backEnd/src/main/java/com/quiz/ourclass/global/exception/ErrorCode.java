package com.quiz.ourclass.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //domain
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일이 존재하지 않습니다."),
    AWS_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AWS 서버 에러입니다.");


    private final HttpStatus status;
    private final String message;
}
