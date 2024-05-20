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

    @Schema(description = "역할")
    private String role;

    @Builder
    private TokenDTO(String accessToken, String refreshToken, String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public static TokenDTO of(String accessToken, String refreshToken, String role) {
        return TokenDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .role(role)
            .build();
    }
}
