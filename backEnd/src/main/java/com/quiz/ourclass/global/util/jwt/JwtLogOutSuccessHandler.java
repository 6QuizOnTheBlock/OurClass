package com.quiz.ourclass.global.util.jwt;

import com.quiz.ourclass.global.dto.FilterResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@RequiredArgsConstructor
public class JwtLogOutSuccessHandler implements LogoutSuccessHandler {

    private final FilterResponse filterResponse;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        String name = String.valueOf(request.getAttribute("name"));
        filterResponse.ok(response, name + "로그아웃을 성공적으로 진행하였습니다.");
    }
}
