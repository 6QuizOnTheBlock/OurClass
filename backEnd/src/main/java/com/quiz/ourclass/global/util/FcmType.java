package com.quiz.ourclass.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FcmType {
    POST("게시글"),
    COMMENT("댓글"),
    ;
    private final String Type;
}
