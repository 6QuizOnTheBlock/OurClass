package com.quiz.ourclass.global.config;

import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.global.util.jwt.JwtAuthFilter;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
import jakarta.servlet.DispatcherType;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

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

    private final String [] whiteList = {
        "/ws-stomp/**", // * 웹 소켓 연결 및 테스팅이 완료되면 삭제
        "/health-check",
        "/swagger-ui.html", "/swagger-ui/**", "/api-docs/**","/swagger-resources/**", "/webjars/**", "/error", "/members/**"

    };

    private final String [] teacherList = {};
    private final String [] studentList = {};





    /*
    * Security Filter Chain => 해당 Bean은 Delegating Filter Proxy에 의해 Servlet 레벨에서 동작한다.
    *
    * (1) API 서버를 만들고 있음으로, 인증 안된 사용자에게 로그인 화면을 Redirect 하는 formLogin 비활성화
    * (2) 마찬가지로 유효한 Basic 토큰이 없으면 인증 화면을 띄우는 httpBasic 도 비활성화
    * (3) API 라서 사이트 위조 공격이 들어오지 않는다. -> csrf 도 비활성화
    * (4) 다른 출처도 우리 리소스를 쓸 수 있게 설정 (같은 출처 = 프로토콜, 호스트, 포트 동일)
    * (5) iframe 설정은 동일 출처에게만 허락한다.
    * (6) Session 을 StateLess 하게 바꾼다. JWT 기반은 Session 을 요청 단위로 쓰고 죽인다.
    * (7) 권한 설정
    * */
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http
            .formLogin(AbstractHttpConfigurer::disable);       // (1)
        http
            .httpBasic(AbstractHttpConfigurer::disable);       // (2)
        http
            .csrf(AbstractHttpConfigurer::disable);            // (3)

        http
            .cors((corsCustomizer -> corsCustomizer.configurationSource(request -> { // (4)

                CorsConfiguration configuration = new CorsConfiguration();

                // a. 우리 프로젝트 리소스를 쓸 수 있는 다른 출처의 목록
                configuration.setAllowedOriginPatterns(Arrays.asList("*"));

                // b. 다른 출처가 사용할 수 있는 매소드 목록
                configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

                // c. 다른 출처로 요청을 보낼 경우, header, 쿠키 등이 안 담겨오는데, 이러한 내용들도 같이 들어오게 한다.
                configuration.setAllowCredentials(true);

                // d. 다른 출처의 요청에 들어 있어도 되는 Header의 목록을 명시한다.
                configuration.setAllowedHeaders(Arrays.asList("*"));

                // e. 응답 헤더에 들어가는 값들을 명시한다. 여기 명시해주지 않으면 header에 값을 집어 넣더라도 안된다.
                configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                return configuration;
            })));

        http
            .headers((headers) -> headers.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin
            ));                                                 // (5)

        http
            .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );                                                  // (6)

        http
            .authorizeHttpRequests(
                (auth) ->
                    auth
                        .dispatcherTypeMatchers(DispatcherType.ASYNC).permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers(whiteList).permitAll()
                        .requestMatchers(teacherList).hasRole("TEACHER")
                        .requestMatchers(studentList).hasRole("STUDENT")
                        .anyRequest().authenticated()
            );                                                  // (7)

        http.addFilterAt(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);  // (8)

        return http.build();
    }


}
