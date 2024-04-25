package com.quiz.ourclass.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private String status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", "성공ㅋ", data);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>("fail", message, null);
    }
}
