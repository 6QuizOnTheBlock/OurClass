package com.quiz.ourclass.global.config;

import com.quiz.ourclass.domain.member.repository.RefreshRepository;
import com.quiz.ourclass.global.dto.FilterResponse;
import com.quiz.ourclass.global.exception.CustomAccessDeniedHandler;
import com.quiz.ourclass.global.exception.CustomAuthenticationEntryPoint;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.jwt.JwtAuthFilter;
import com.quiz.ourclass.global.util.jwt.JwtAutoLoginFilter;
import com.quiz.ourclass.global.util.jwt.JwtLogOutHandler;
import com.quiz.ourclass.global.util.jwt.JwtLogOutSuccessHandler;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
import com.quiz.ourclass.global.util.jwt.TokenRefreshFilter;
import jakarta.servlet.DispatcherType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    /*
     *  Security Config의 변수 설명
     *  (1) JwtUtil                  : Jwt 토큰 발급, 검증 등의 로직 담당
     *  (2) AuthenticationEntryPoint : 인증 실패 시 예외 처리
     *  (3) AccessDeniedHandler      : 해당 경로를 요청할 권한이 없을 때 예외 처리
     *  (4) whiteList                : 화이트 리스트 => 모든 유저가 권한 제약 없이 요청 가능
     *  (5) teacherList              : 선생님 토큰으로만 입장 가능, 학생 불가
     *  (6) studentList              : 학생 토큰으로만 입장 가능
     */


    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final RefreshRepository refreshRepository;
    private final FilterResponse filterResponse;
    private final JwtLogOutHandler jwtLogOutHandler;
    private final JwtLogOutSuccessHandler jwtLogOutSuccessHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAutoLoginFilter jwtAutoLoginFilter;
    private final TokenRefreshFilter tokenRefreshFilter;
    private final String[] whiteList = {
        "/ws-stomp/**", // * 웹 소켓 연결 및 테스팅이 완료되면 삭제
        "/health-check",
        "/swagger-ui.html", "/swagger-ui/**", "/api-docs/**", "/swagger-resources/**",
        "/webjars/**", "/error", "/members/", "/members/sign-in", "/members/developer-At",
        "/members/default-image"
    };

    private final String[] teacherList = {};
    private final String[] studentList = {};


    /*
     * Security Filter Chain => 해당 Bean은 Delegating Filter Proxy에 의해 Servlet 레벨에서 동작한다.
     *
     * (1) API 서버를 만들고 있음으로, 인증 안된 사용자에게 로그인 화면을 Redirect 하는 formLogin 비활성화
     * (2) 마찬가지로 유효한 Basic 토큰이 없으면 인증 화면을 띄우는 httpBasic 도 비활성화
     * (3) API 라서 사이트 위조 공격이 들어오지 않는다. -> csrf 도 비활성화
     * (4) 다른 출처도 우리 리소스를 쓸 수 있게 설정 (같은 출처 = 프로토콜, 호스트, 포트 동일)
     * (5) iframe 설정은 동일 출처에게만 허락한다.
     * (6) 로그아웃 설정 -> [yaml 파일에 정한 Context root]는 빼줘야 한다.
     * (6) Session 을 StateLess 하게 바꾼다. JWT 기반은 Session 을 요청 단위로 쓰고 죽인다.
     * (7) 권한 설정
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .formLogin(AbstractHttpConfigurer::disable);       // (1)
        http
            .httpBasic(AbstractHttpConfigurer::disable);       // (2)
        http
            .csrf(AbstractHttpConfigurer::disable);            // (3)

        http
            .cors(
                (corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource())));

        http
            .headers((headers) -> headers.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin
            ));                                                 // (5)

        http.logout(auth ->                                     // (6)
            auth
                .logoutUrl("/members/logout")
                .addLogoutHandler(jwtLogOutHandler)
                .logoutSuccessHandler(jwtLogOutSuccessHandler)
        );

        http
            .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );                                                  // (7)

        http
            .authorizeHttpRequests(
                (auth) ->
                    auth                                        // (8)
                        .dispatcherTypeMatchers(DispatcherType.ASYNC).permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers(whiteList).permitAll()
                        .requestMatchers(teacherList).hasRole("TEACHER")
                        .requestMatchers(studentList).hasRole("STUDENT")
                        .anyRequest().authenticated()
            );                                                  // (9)

        http.exceptionHandling(auth ->
            auth.authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)); // (10)

        http.addFilterAt(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);        // (11)

        http.addFilterBefore(tokenRefreshFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterAfter(jwtAutoLoginFilter, LogoutFilter.class);

        return http.build();
    }

    @Bean
// CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedOrigins(List.of("*"));
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 구성 적용
        return source;
    }

}
