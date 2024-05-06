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
    GUEST_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "게스트는 권한이 없습니다."),
    NOT_FOUND_DEFAULT_IMAGE(HttpStatus.NOT_FOUND, "해당 번호의 기본 이미지를 찾을 수 없습니다."),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    //member
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    EXISTING_MEMBER(HttpStatus.NOT_FOUND, "존재하는 회원입니다."),
    OIDC_INVALID_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 OIDC 토큰 입니다. "),
    CERTIFICATION_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "검증에 실패했습니다."),
    DEFAULT_IMAGES_UNDER_4(HttpStatus.INTERNAL_SERVER_ERROR, "기본 이미지의 개수가 4보다 적습니다."),


    //security
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰 입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 입니다."),
    CANT_FIND_REFRESH(HttpStatus.UNAUTHORIZED, "해당 토큰을 찾을 수 없습니다."),
    ALREADY_LOGOUT(HttpStatus.UNAUTHORIZED, "이미 로그아웃한 토큰 입니다."),

    //s3
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일이 존재하지 않습니다."),
    AWS_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AWS 서버 에러입니다."),

    //post
    POST_WRITE_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "알림장을 작성할 수 있는 권한이 없습니다."),
    POST_EDIT_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "멤버가 작성한 게시글을 찾을 수 없습니다."),
    POST_DELETE_STUDENT_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "게시글 작성자와 요청자가 다릅니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),

    //comment
    COMMENT_EDIT_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "댓글 작성자 단체와 현재 사용자 단체가 다릅니다."),
    COMMENT_DELETE_STUDENT_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "댓글 작성자와 요청자가 다릅니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

    //image
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다."),

    //userAccess
    MEMBER_NOT_IN_ORGANIZATION(HttpStatus.FORBIDDEN, "멤버가 해당 단체 소속이 아닙니다."),
    MEMBER_NOT_MANAGER(HttpStatus.FORBIDDEN, "해당 학급의 관리자가 아닙니다."),

    //organization
    ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "학급을 찾을 수 없습니다."),
    ORGANIZATION_CODE_NOT_FOUND(HttpStatus.BAD_REQUEST, "학급 가입이 불가능한 상태입니다."),
    ORGANIZATION_CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "학급 가입코드가 일치하지 않습니다."),
    ALREADY_IN_ORGANIZATION(HttpStatus.BAD_REQUEST, "이미 가입한 학급입니다."),

    //challenge
    REPORT_NOW_FOUND(HttpStatus.NOT_FOUND, "레포트를 찾을 수 없습니다."),
    CHALLENGE_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "그룹을 찾을 수 없습니다."),
    CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "함께달리기를 찾을 수 없습니다."),
    RUNNING_CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "진행중인 함께달리기가 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
