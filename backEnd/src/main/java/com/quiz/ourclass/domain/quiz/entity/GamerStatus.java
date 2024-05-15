package com.quiz.ourclass.domain.quiz.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GamerStatus {
    ENTER, LEAVE;

    // from String to Enum converter
    @JsonCreator
    public static GamerStatus from(String s) {
        return GamerStatus.valueOf(s.toUpperCase());
    }
}
