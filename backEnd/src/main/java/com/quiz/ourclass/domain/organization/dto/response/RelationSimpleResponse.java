package com.quiz.ourclass.domain.organization.dto.response;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "멤버 관계 랭킹 조회 응답 DTO")
public record RelationSimpleResponse(
    MemberSimpleDTO member,
    @Schema(description = "관계 포인트")
    int relationPoint
) {

}
