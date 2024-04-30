package com.quiz.ourclass.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi Api() {
        return GroupedOpenApi.builder()
            .group("all-api")
            .pathsToMatch("/**")
            .build();
    }

    @Bean
    public GroupedOpenApi memberApi() {
        return GroupedOpenApi.builder()
            .group("member-api")
            .pathsToMatch("/members/**")
            .build();
    }

    @Bean
    public GroupedOpenApi boardApi() {
        return GroupedOpenApi.builder()
            .group("board-api")
            .pathsToMatch("/board/**")
            .build();
    }

    @Bean
    public GroupedOpenApi ChallengeApi() {
        return GroupedOpenApi.builder()
            .group("challenge-api")
            .pathsToMatch("/challenge/**")
            .build();
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .title("울반 - OurClass API문서")
            .version("v0.0.1")
            .description("OurClass API 명세서.");

        SecurityScheme bearer = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("Authorization")
            .in(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION);

        // Security 요청 설정
        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("Authorization");

        Components components = new Components()
            .addSecuritySchemes("Authorization", bearer);

        return new OpenAPI()
            .components(components)
            .addSecurityItem(addSecurityItem)
            .info(info);
    }
}