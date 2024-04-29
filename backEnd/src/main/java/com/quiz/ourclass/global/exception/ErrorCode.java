package com.quiz.ourclass.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //global
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버가 존재하지 않습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "첨부한 파일이 S3에 업로드 되지 않았습니다."),

    //member
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    EXISTING_MEMBER(HttpStatus.NOT_FOUND, "존재하는 회원입니다."),

    //security
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰 입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 입니다."),

    //s3
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일이 존재하지 않습니다."),
    AWS_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AWS 서버 에러입니다."),

    //post
    POST_EDIT_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "멤버가 작성한 게시글을 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),

    //image
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다."),

    //userAccess
    MEMBER_NOT_IN_ORGANIZATION(HttpStatus.FORBIDDEN, "멤버가 해당 단체 소속이 아닙니다."),

    //organization
    ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "학급을 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
