package com.quiz.ourclass.global.config;

import com.quiz.ourclass.global.util.ConstantUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
            .group("all-api")
            .pathsToMatch("/**")
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
            .bearerFormat(ConstantUtil.AUTHORIZATION)
            .in(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION);

        // Security 요청 설정
        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList(ConstantUtil.AUTHORIZATION);

        Components components = new Components()
            .addSecuritySchemes(ConstantUtil.AUTHORIZATION, bearer);

        return new OpenAPI()
            .components(components)
            .addSecurityItem(addSecurityItem)
            .addServersItem(new Server().url("https://k10d107.p.ssafy.io/api")
                .description("Default Server URL"))
            .addServersItem(new Server().url("http://localhost:8080/api")
                .description("Local Development Server"))
            .info(info);
    }
}