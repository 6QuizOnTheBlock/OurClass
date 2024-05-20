package com.quiz.ourclass.domain.organization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "학급 초대 코드 DTO")
@Builder
public record InviteCodeDTO(
    @Schema(description = "코드")
    String code
) {

}
