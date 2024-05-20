package com.quiz.ourclass.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 사진 DTO")
public record MemberUpdateResponse(
    @Schema(description = "변환된 사진")
    String photoImageUrl
) {

}
