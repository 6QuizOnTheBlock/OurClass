package com.quiz.ourclass.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {

    private String accessToken;
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
