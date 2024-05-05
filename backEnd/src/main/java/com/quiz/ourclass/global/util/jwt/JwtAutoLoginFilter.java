package com.quiz.ourclass.global.util.jwt;

import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.entity.Refresh;
import com.quiz.ourclass.domain.member.repository.RefreshRepository;
import com.quiz.ourclass.global.dto.FilterResponse;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.util.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAutoLoginFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final FilterResponse filterResponse;
    private final RefreshRepository refreshRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        if (request.getMethod().equals("PATCH") &&
            request.getRequestURI().equals("/api/members/token")) {
            String accessToken = jwtUtil.separateBearer(request.getHeader("Authorization"));
            int tokenStatus = jwtUtil.validateToken(accessToken);

            switch (tokenStatus) {
                case 0:
                    respondProcess(accessToken, jwtUtil.getUserInfoFromToken(accessToken),
                        response);
                    break;
                case -1:
                    filterResponse.error(response, ErrorCode.EXPIRED_TOKEN);
                    break;
                case -2:
                case -3:
                case -4:
                    filterResponse.error(response, ErrorCode.INVALID_TOKEN);
                    break;
                case -5:
                    filterResponse.error(response, ErrorCode.ALREADY_LOGOUT);
                    break;
            }

            return;
        }

        // 이 필터를 필요로 하는 요청이 아닐 경우, 다음 필터로 넘어간다.
        filterChain.doFilter(request, response);
    }


    /*
     * 한 시간 이내이면, 새로운 토큰 만들어서 주기
     * 그거 보다 수명이 길면, 기존 refreshToken 그대로 주기
     * */
    private void respondProcess(String accessToken, Claims info, HttpServletResponse response)
        throws IOException {
        if (jwtUtil.isTokenExpiringWithin(info.getIssuedAt(), info.getExpiration(), 1)) {
            renewAndRespondToken(accessToken, info, response);
        } else {
            respondExistingToken(accessToken, info, response);
        }
    }


    /*
     * 1) 기존 AccessToken BlackList 에 추가
     * 2) 새 토큰 발급
     * */

    private void renewAndRespondToken(String accessToken, Claims info, HttpServletResponse response)
        throws IOException {
        redisUtil.valueSet("AT:" + accessToken, info.getSubject(),
            Duration.ofMillis(info.getExpiration().getTime() - info.getIssuedAt().getTime()));
        TokenDTO tokens = jwtUtil.refreshToken(Long.parseLong(info.getSubject()),
            String.valueOf(info.get("ROLE")));
        filterResponse.ok(response, tokens);
    }

    /*
     * 1) 기존 토큰의 Refresh Token 받아서 그대로 주기
     * */

    private void respondExistingToken(String accessToken, Claims info, HttpServletResponse response)
        throws IOException {
        Refresh refresh = refreshRepository.findByAccessToken(accessToken).orElse(null);
        if (refresh == null) {
            filterResponse.error(response, ErrorCode.INVALID_TOKEN);
        } else {
            filterResponse.ok(response, TokenDTO.of(accessToken, refresh.getRefreshToken(),
                String.valueOf(info.get("ROLE"))));
        }
    }
}
