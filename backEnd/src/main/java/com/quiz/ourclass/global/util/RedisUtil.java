package com.quiz.ourclass.global.util;

import java.time.Duration;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public void valueSet(String key, String value, Duration time) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, time);
    }

    public String valueGet(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void setAdd(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<String> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Long setMembers(String key, String value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
