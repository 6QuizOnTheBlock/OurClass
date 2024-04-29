package com.quiz.ourclass.global.exception;

import com.quiz.ourclass.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final HttpHeaders jsonHeaders;

    static {
        jsonHeaders = new HttpHeaders();
        jsonHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(
        GlobalException globalException) {
        ApiResponse<Object> response = ApiResponse.fail(
            globalException.getErrorCode().getMessage());
        return new ResponseEntity<>(response, jsonHeaders,
            globalException.getErrorCode().getStatus());
    }
}
