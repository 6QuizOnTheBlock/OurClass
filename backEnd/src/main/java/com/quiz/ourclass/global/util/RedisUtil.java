package com.quiz.ourclass.global.util;


import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

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
        return ConstantUtil.BLACKLIST_ACCESS_TOKEN + accessToken;
    }

    public void addChatRoomUser(Long chatRoomId, String memberId) {
        String key = buildChatRoomKey(chatRoomId);
        log.info("redis insert room id : {}", key);
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

    public void setQuizGame(Long quizGameId, UUID uuid) {
        redisTemplate.opsForValue()
            .set(buildQuizGameUrlKey(uuid), String.valueOf(quizGameId), 10, TimeUnit.MINUTES);
    }

    public String getQuizGame(String uuid) {

        log.info(buildQuizGameUrlKey(uuid));

        return Optional.ofNullable(redisTemplate.opsForValue()
                .get(buildQuizGameUrlKey(uuid)))
            .orElseThrow(() -> new GlobalException(ErrorCode.QUIZ_GAME_INACTIVATE));
    }

    private String buildChatRoomKey(Long chatRoomId) {
        return ConstantUtil.REDIS_CHAT_ROOM_KEY + chatRoomId;
    }

    private String buildQuizGameUrlKey(UUID uuid) {
        return ConstantUtil.QUIZ_GAME + uuid.toString();
    }

    private String buildQuizGameUrlKey(String uuid) {
        return ConstantUtil.QUIZ_GAME + uuid;
    }

}
