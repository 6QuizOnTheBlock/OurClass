package com.quiz.ourclass.global.exception;

import com.quiz.ourclass.global.dto.FilterResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final FilterResponse filterResponse;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {

        // 오류 확인
        String exception = String.valueOf(request.getAttribute("exception"));

        // 에러 반환
        if (!exception.equals("null")) {
            // 1. 토큰이 인증에 불충분한 경우
            if (ErrorCode.valueOf(exception).equals(ErrorCode.EXPIRED_TOKEN)) {
                filterResponse.error(response, ErrorCode.EXPIRED_TOKEN);
                return;
            }
            if (ErrorCode.valueOf(exception).equals(ErrorCode.INVALID_TOKEN)) {
                filterResponse.error(response, ErrorCode.INVALID_TOKEN);
                return;
            }
            if (ErrorCode.valueOf(exception).equals(ErrorCode.ALREADY_LOGOUT)) {
                filterResponse.error(response, ErrorCode.ALREADY_LOGOUT);
                return;
            }
            // 2. 그 외의 인증 오류에 대한 핸들링
            log.error("토큰 외의 인증 에러 입니다. 내용은 다음과 같습니다. \n {}", authException.getMessage());
            filterResponse.error(response, ErrorCode.ANOTHER_AUTH_ERROR);
        } else {
            filterResponse.error(response, ErrorCode.EMPTY_TOKEN);
        }
    }
}
