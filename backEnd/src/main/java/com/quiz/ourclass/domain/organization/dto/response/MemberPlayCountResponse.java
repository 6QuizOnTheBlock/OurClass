package com.quiz.ourclass.domain.organization.dto.response;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "멤버 참여 횟수 조회 DTO")
public record MemberPlayCountResponse(
    MemberSimpleDTO member,
    @Schema(description = "참여 횟수")
    int count
) {

}
