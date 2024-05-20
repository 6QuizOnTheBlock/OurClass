package com.quiz.ourclass.domain.organization.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "관계 조회 요청 DTO")
public record RelationRequest(
    @Schema(description = "소스 멤버 id")
    long sourceMemberId,
    @Schema(description = "타겟 멤버 id")
    long targetMemberId
) {

}
