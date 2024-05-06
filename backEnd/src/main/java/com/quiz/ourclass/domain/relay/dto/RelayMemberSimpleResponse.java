package com.quiz.ourclass.domain.relay.dto;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "릴레이 멤버 응답 DTO")
public record RelayMemberSimpleResponse(
    @Schema(description = "릴레이 멤버 id")
    long id,
    @Schema(description = "릴레이 순서")
    int turn,
    @Schema(description = "주자 정보")
    MemberSimpleDTO member
) {

}
