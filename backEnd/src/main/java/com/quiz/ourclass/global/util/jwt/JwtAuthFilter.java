package com.quiz.ourclass.global.util.jwt;

import com.quiz.ourclass.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // A. 토큰 체킹
        // 토큰 가져오기
        String token = jwtUtil.separateBearer(request.getHeader("Authorization"));

        // 토큰이 없다면 다음 [Filter]로 넘어가기
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 유효성 체크 -> 통과 못하면 바로 다음 filter 로 넘어간다.
        switch (jwtUtil.validateToken(token)){
            case -1:
                request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN);
                filterChain.doFilter(request, response);
                return;
            case -2:
            case -3:
            case -4:
                request.setAttribute("exception", ErrorCode.INVALID_TOKEN);
                filterChain.doFilter(request,response);
                return;
        }

        // B. Token 으로 인증 객체 만들기

        Claims info = jwtUtil.getUserInfoFromToken(token);

        log.info("Token 들어 있는 값={}", info.toString());

        try {
            setAuthentication(info.getSubject());
        } catch (UsernameNotFoundException e){
            request.setAttribute("exception", ErrorCode.NOT_FOUND_MEMBER.getMessage());
            log.error("관련 에러: {}", e.getMessage());
        }

        filterChain.doFilter(request,response);
    }

    // 인증 객체 생성 후, SecurityPlaceHolder 임시 세션에 등록
    private void setAuthentication(String id) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(id);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }
}
