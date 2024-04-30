package com.quiz.ourclass.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultResponse<T> {

    private String status;
    private String message;
    private T data;

    public static <T> ResultResponse<T> success(T data) {
        return new ResultResponse<>("success", "성공ㅋ", data);
    }

    public static <T> ResultResponse<T> fail(String message) {
        return new ResultResponse<>("fail", message, null);
    }
}
