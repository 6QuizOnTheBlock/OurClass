package com.quiz.ourclass.global.config;

import java.time.Duration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class CacheConfig {

    /* Redis Cache 기본 설정
     * entryTtl: 캐시 만료 시간
     * disableCashNullValues(): 캐싱할 때 null 값을 허용하지 않는다.
     * StringRedisSerializer(): [Key]를 직렬화 할 때 사용하는 규칙. 보통 String 형태를 많이 사용
     * GenericJackson2JsonRedisSerializer(): [Value]를 직렬화 할 때, 사용하는 규칙 [Jackon2]를 많이 사용함.
     * */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(50))
            .disableCachingNullValues()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new GenericJackson2JsonRedisSerializer())
            );
    }

    /*
     * 캐시 이름 별로 맞춤 세팅을 할 때 사용하는 클래스: RedisCacheManagerBuilderCustomizer
     * OICD 캐시: ID TOKEN 유효성 검증 시 필요한 공개키 ID 목록을 받는 매소드에서 사용
     *           해당 매소드는 일정 시간 내에 너무 많이 요청하면 요청이 차단됨.
     *           일주일 단위를 두고 확인한다.
     * */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
            .withCacheConfiguration("OIDC",
                RedisCacheConfiguration.defaultCacheConfig()
                    .computePrefixWith(cacheName -> "OIDC:")
                    .entryTtl(Duration.ofDays(7))
                    .disableCachingNullValues()
                    .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            new StringRedisSerializer())
                    )
                    .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            new GenericJackson2JsonRedisSerializer())
                    )
            );
    }

}
