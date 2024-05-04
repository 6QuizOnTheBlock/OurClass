package com.quiz.ourclass.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refresh")
@Getter
@Setter
public class Refresh {

    @Id
    private long memberId;
    private String refreshToken;

    @Indexed
    private String accessToken;


    // 토큰 수명 -> SpEL 문으로 기본값도 설정
    @TimeToLive
    private long lifeTime;


    @Builder
    private Refresh(long memberId, String accessToken, String refreshToken, long lifeTime) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.lifeTime = lifeTime;
    }

    public static Refresh of(long memberId, String accessToken, String refreshToken,
        long lifeTime) {
        return builder()
            .memberId(memberId)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .lifeTime(lifeTime).build();
    }
}
