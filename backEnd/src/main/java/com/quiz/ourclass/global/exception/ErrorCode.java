package com.quiz.ourclass.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //global
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버가 존재하지 않습니다."),

    //domain
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일이 존재하지 않습니다."),
    AWS_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AWS 서버 에러입니다."),

    //post
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "첨부한 파일이 S3에 업로드 되지 않았습니다."),
    TYPE_NAME_ERROR(HttpStatus.BAD_REQUEST, "게시판 유형에 맞지 않는 입력입니다.");

    private final HttpStatus status;
    private final String message;
}
