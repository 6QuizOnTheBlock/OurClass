package com.quiz.ourclass.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {

    @Schema(description = "접근 토큰")
    private String accessToken;
    @Schema(description = "갱신 토큰")
    private String refreshToken;

    @Builder
    private TokenDTO (String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenDTO of (String accessToken, String refreshToken){
        return TokenDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
