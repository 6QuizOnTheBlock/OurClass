package com.quiz.ourclass.global.util.jwt;

import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.repository.RefreshRepository;
import com.quiz.ourclass.global.dto.FilterResponse;
import com.quiz.ourclass.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class TokenRefreshFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final FilterResponse filterResponse;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // 1) 그냥 [Servlet Request]를 HttpsServletRequest 로 변환, [Response]도 마찬가지
        // -> HTTP 프로토콜을 사용하는 요청에 특화된 데이터와 메소드 사용가능
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 2) [Header]에서 RefreshToken 받기
        String refreshToken = jwtUtil.separateBearer(httpRequest.getHeader("refreshToken"));
        String accessToken = jwtUtil.separateBearer(httpRequest.getHeader("Authorization"));

        // 3) 경로가 다음과 같고, refreshToken 이 null 이 아니며, Method가 GET 일 경우
        //    refresh 토큰이 [Redis]에 있는지 확인한다.
        if ("/api/members/token".equals(httpRequest.getRequestURI())
            && refreshToken != null
            && httpRequest.getMethod().equals("GET")) {
            // 3-1) Redis 안에 들어있으면, 만료 되지 않고, 유효한 토큰이다.
            // -- if문 Chaining 설명
            // a) accessToken 의 Pair 인 Refresh 토큰 찾으셈
            // b) 그게 진짜 보내온 [Refresh]랑 같음?
            // c) 앞의 값이 null 이면 Error 내지 말고 false 반환
            if (refreshRepository.findByAccessToken(accessToken)
                .map(refresh -> refresh.getRefreshToken().equals(refreshToken)).orElse(false)) {

                // 3-2) [RefreshToken]이 있으면 새로운 AT, RT 발급 및 Redis 갱신, 요청 응답 전달 후 종료
                Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
                TokenDTO tokens = jwtUtil.refreshToken(Long.parseLong(info.getSubject()),
                    String.valueOf(info.get("ROLE")));

                // 3-3) 이제 저장한 정보들을 Response 객체에 담는다.
                filterResponse.ok(httpResponse, tokens);
                return;
            } else {
                filterResponse.error(httpResponse, ErrorCode.CANT_FIND_REFRESH.getMessage());
                return;
            }
        }

        doFilter(request, response, filterChain);
    }
}
