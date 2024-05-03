package com.quiz.ourclass.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public void valueSet(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void valueSet(String key, String value, Long time) {
        redisTemplate.opsForValue().set(key, value, time);
    }

    public String valueSet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
