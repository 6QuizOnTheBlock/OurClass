package com.quiz.ourclass.global.util;


import java.time.Duration;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private final String PREFIX = "AT:";

    public void valueSet(String key, String value, Duration time) {
        redisTemplate.opsForValue().set(key, value, time);
    }

    public String valueGet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setAdd(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<String> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Long removeMembers(String key, String value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


    public String generateBlackListKey(String accessToken) {
        return PREFIX + accessToken;
    }

    public void addChatRoomUser(Long chatRoomId, String memberId) {
        String key = buildChatRoomKey(chatRoomId);
        redisTemplate.opsForSet().add(key, memberId);
    }

    public Set<String> getChatRoomUsers(Long chatRoomId) {
        String key = buildChatRoomKey(chatRoomId);
        return redisTemplate.opsForSet().members(key);
    }

    public void removeChatRoomUser(Long chatRoomId, String memberId) {
        String key = buildChatRoomKey(chatRoomId);
        redisTemplate.opsForSet().remove(key, memberId);
    }

    private String buildChatRoomKey(Long chatRoomId) {
        return ConstantUtil.REDIS_CHAT_ROOM_KEY + chatRoomId;
    }

}
