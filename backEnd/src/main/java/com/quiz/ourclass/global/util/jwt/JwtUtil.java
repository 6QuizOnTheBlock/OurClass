package com.quiz.ourclass.global.util.jwt;


import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.entity.Refresh;
import com.quiz.ourclass.domain.member.repository.RefreshRepository;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j                  // Log 작성 위해
@Component              // bean 자동 등록
@RequiredArgsConstructor// 생성자 생략 위해
public class JwtUtil {


    /*
     * JwtUtil 변수 명세
     * (1) ingredient: HS 알고리즘에서 쓸 수 있는 알고리즘 [Key]를 만들기 위한 재료
     *                 String 형태의 SecretKey -> Base64로 decoding 해서 bytes 로 변환 -> [Security Key]로 재구성
     *
     * (2) key: JWT의 C(전자서명) 파트를 만들 때 쓰이는 KEY이다. (1)번을 재구성해서 만들었다.
     *
     * (3) signatureAlgorithm: JWT의 전자서명 파트를 만들 때 쓰이는 알고리즘이다.
     *
     * (4) ACCESS_TOKEN_TIME: 접근 토큰 수명
     * (5) REFRESH_TOKEN_TIME: 갱신 토큰 수명
     * (6) Bearer Token Prefix
     * */


    @Value("${jwt.secret}")
    private String ingredient;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    @Value("#{new Integer('${jwt.token.access-expiration-time}')}")
    private Integer ACCESS_TOKEN_TIME;

    @Value("#{new Integer('${jwt.token.refresh-expiration-time}')}")
    private Integer REFRESH_TOKEN_TIME;
    private static final String BEARER_PREFIX = "Bearer ";

    private final UserDetailsServiceImpl userDetailsService;

    // Redis Repository 에 집어넣기 위함.
    private final RefreshRepository refreshRepository;
    private final RedisUtil redisUtil;


    /* A. Init 함수 -> JWT 토큰 만들기 위한 사전 준비  */
    // 객체 생성 후 바로 동작
    @PostConstruct
    public void init() {

        // String 형태의 [secretKey]를 [Byte]로 디코딩
        byte[] bytes = Base64.getDecoder().decode(ingredient);
        // hmacShaKeyFor() = 매개 변수로 들어온 byte 배열을 HS 알고리즘에서 사용할 수 있는 키로 탈바꿈
        key = Keys.hmacShaKeyFor(bytes);
    }


    /* B. createToken
     *  (1) isAccess boolean 값에 따라, AccessToken 혹은 RefreshToken 을 만든다.
     * */
    public String createToken(long memberId, String role, boolean isAccess) {

        // 오늘 날짜 확인
        Date now = new Date();

        //

        // 토큰 생성 후 반환 :
        //  header: 사용한 알고리즘에 따라 관련 메타데이터가 채워짐
        //  payload: 제목, 발행일, 만료일이 들어갔다. 추가하고 싶다면 Claim 객체를 만들어 내용을 채우고 추가하면 된다.
        //  signWith: JWT 토큰 위조 방지를 위한 서명
        return Jwts.builder()
            .setSubject(String.valueOf(memberId))
            .claim("ROLE", role)
            .setIssuedAt(new Date(now.getTime()))
            .setExpiration(
                new Date(now.getTime() + (isAccess ? ACCESS_TOKEN_TIME : REFRESH_TOKEN_TIME)))
            .signWith(key, signatureAlgorithm)
            .compact();

    }


    /* C. separateBearer
     *  Access Token 혹은 Refresh Token 의 접두사 분리
     * */
    public String separateBearer(String bearerToken) {

        // [bearerToken]이 [Null]이 아니다. 또한 Bearer 로 시작하면, 앞의 7 글자를 잘라서 반환하라.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /*
     * D. 토큰 유효성 검증
     * 0: 정상, -1: 만료된 토큰, -2: 유효하지 않은 서명, -3: 지원되지 않는 토큰, -4: 잘못된 JWT 토큰
     * */

    public int validateToken(String token) {

        if (redisUtil.hasKey("AT:" + token)) {
            return -5;
        }
        try {
            // JWT 토큰을 Parsing 하는 객체를 만들어서 객체의 파싱 매소드에 매개변수로 token 을 넣었다.
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return 0;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            log.error("관련에러: {}", e.getMessage());
            return -1;
        } catch (io.jsonwebtoken.security.SignatureException | SecurityException |
                 MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            log.error("관련에러: {}", e.getMessage());
            return -2;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            log.error("관련에러: {}", e.getMessage());
            return -3;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            log.error("관련에러: {}", e.getMessage());
            return -4;
        }
    }

    /*
     *  E. 토큰 해부해서 Payload 전체를 반환하기
     * */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /*
     *  F. [Member Id]에 해당하는 회원 정보를 [DB]에서 가져와 인증 객체를 생성
     * */

    public Authentication createAuthentication(String id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }

    /*
     *  G. [RefreshToken Redis]에 집어넣기
     * */

    public void saveRefresh(long memberId, String accessToken, String refreshToken) {
        refreshRepository.save(
            Refresh.of(memberId, accessToken, refreshToken, REFRESH_TOKEN_TIME / 1000));
    }

    public TokenDTO refreshToken(long memberId, String role) {

        String accessToken = createToken(memberId, role, true);
        String refreshToken = createToken(memberId, role, false);
        saveRefresh(memberId, accessToken, refreshToken);

        return TokenDTO.of(accessToken, refreshToken, role);
    }

    public boolean isTokenExpiringWithin(Date issuedAt, Date expiration, long hour) {
        // Duration 패키지는 java.time 의 일부 따라서 Date 를 Instant 라는 time 객체로 바꿔줘야함.
        Duration tokenDuration = Duration.between(issuedAt.toInstant(), expiration.toInstant());
        Duration targetHour = Duration.ofHours(hour);
        // 토큰 수명이 목표 시간보다 작거나 같으면 true 반환
        return tokenDuration.compareTo(targetHour) <= 0;

    }

}
