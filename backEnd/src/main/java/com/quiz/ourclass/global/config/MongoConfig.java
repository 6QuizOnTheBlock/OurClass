package com.quiz.ourclass.global.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.quiz.ourclass.global.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.quiz.ourclass.domain.chat.repository")
public class MongoConfig {

    @Value("${mongo.url}")
    String mongoUrl;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUrl);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), ConstantUtil.MONGO_DB_NAME);
    }
}
