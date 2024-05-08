package com.quiz.ourclass.global.util.jwt;

import com.quiz.ourclass.global.dto.FilterResponse;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.util.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtLogOutHandler implements LogoutHandler {

    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;
    private final FilterResponse filterResponse;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {
        String accessToken = jwtUtil.separateBearer(request.getHeader("Authorization"));
        int valid = jwtUtil.validateToken(accessToken);
        try {
            if (valid == 0) {
                Claims info = jwtUtil.getUserInfoFromToken(accessToken);
                redisUtil.valueSet(redisUtil.generateBlackListKey(accessToken), info.getSubject(),
                    Duration.ofMillis(
                        info.getExpiration().getTime() - info.getIssuedAt().getTime()));
                request.setAttribute("name", info.getSubject());
            } else if (valid == 1) {
                filterResponse.error(response, ErrorCode.EXPIRED_TOKEN);
            } else if (valid > 1) {
                filterResponse.error(response, ErrorCode.INVALID_TOKEN);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


}
