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
import org.springframework.data.redis.core.ZSetOperations;
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

    private String buildRankingKey(long quizGameId) {
        return ConstantUtil.RANKING + quizGameId;
    }


    // 신규 게이머를 퀴즈 게임 랭크에 넣기
    public void setMemberScore(long memberId, long quizGameId, int score) {
        redisTemplate.opsForZSet()
            .add(buildRankingKey(quizGameId), String.valueOf(memberId), score);
    }

    // 전체 랭킹을 점수가 높은 순으로 가져오기
    public Set<ZSetOperations.TypedTuple<String>> getAllMemberScores(long quizGameId) {
        return redisTemplate.opsForZSet()
            .reverseRangeWithScores(buildRankingKey(quizGameId), 0, -1);
    }

    // 멤버 한 명 점수 조회
    public Double getMemberScore(long memberId, long quizGameId) {
        return redisTemplate.opsForZSet()
            .score(buildRankingKey(quizGameId), String.valueOf(memberId));
    }

    public boolean removeMemberScore(long memberId, long quizGameId) {
        Long result = redisTemplate.opsForZSet()
            .remove(buildRankingKey(quizGameId), Long.toString(memberId));
        return result != null && result > 0;
    }


}
