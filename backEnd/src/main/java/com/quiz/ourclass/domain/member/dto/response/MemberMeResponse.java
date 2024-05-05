package com.quiz.ourclass.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "자기 정보 DTO")
public record MemberMeResponse(
    @Schema(description = "고유번호")
    long id,
    @Schema(description = "이메일")
    String email,
    @Schema(description = "이름")
    String name,
    @Schema(description = "프로필 사진")
    String photo,
    @Schema(description = "역할")
    String role
) {

}
