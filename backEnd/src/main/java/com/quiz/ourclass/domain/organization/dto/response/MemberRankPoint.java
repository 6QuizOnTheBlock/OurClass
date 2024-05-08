package com.quiz.ourclass.domain.organization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "랭킹 조회 응답 DTO")
public record MemberRankPoint(
    @Schema(description = "학생 이름")
    String name,
    @Schema(description = "학생 점수")
    int point
) {

}
