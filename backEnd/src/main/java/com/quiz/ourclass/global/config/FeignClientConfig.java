package com.quiz.ourclass.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.quiz.ourclass.domain.member")
public class FeignClientConfig {

}
