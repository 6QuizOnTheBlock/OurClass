package com.quiz.ourclass.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FcmType {
    POST("게시글"),
    COMMENT("댓글"),
    NOTICE("알림장"),
    CHALLENGE("함께달리기"),
    RELAY("이어달리기"),
    ;
    private final String Type;
}
