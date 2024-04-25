package com.quiz.ourclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OurClassApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OurClassApplication.class);
        app.setAdditionalProfiles("dev");
        app.run(args);
    }

}
