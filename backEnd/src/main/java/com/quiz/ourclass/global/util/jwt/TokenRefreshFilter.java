package com.quiz.ourclass.global.util.jwt;

import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Refresh;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.member.repository.RefreshRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenRefreshFilter extends GenericFilter {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final MemberRepository memberRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain filterChain) throws IOException, ServletException {

        // 1) 그냥 [Servlet Request]를 HttpsServletRequest 로 변환, [Response]도 마찬가지
        // -> HTTP 프로토콜을 사용하는 요청에 특화된 데이터와 메소드 사용가능
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 2) [Header]에서 RefreshToken 받기
        String refreshToken = httpRequest.getHeader("refreshToken");

        // 3) 경로가 다음과 같고, refreshToken 이 null 이 아닐경우, refresh 토큰이 [Redis]에 있는지 확인한다.
        if ("/api/members/token".equals(httpRequest.getRequestURI())) {
            if (refreshToken != null) {

                // 토큰 검사
                switch (jwtUtil.validateToken(refreshToken)) {
                    case -1:
                        request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN);
                        filterChain.doFilter(request, response);
                        return;
                    case -2:
                    case -3:
                    case -4:
                        request.setAttribute("exception", ErrorCode.INVALID_TOKEN);
                        filterChain.doFilter(request, response);
                        return;
                }

                Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
                Refresh refresh = refreshRepository.findByMemberId(
                    Long.parseLong(info.getSubject())).orElseThrow();
            }
        }


    }

    private TokenDTO refreshToken(long memberId) {

        Member member = memberRepository.findById(memberId).orElse(null);

        if (member == null) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }

        String accessToken = jwtUtil.createToken(member, true);
        String refreshToken = jwtUtil.createToken(member, false);
        jwtUtil.saveRefresh(memberId, refreshToken);

        return null;
    }
}
