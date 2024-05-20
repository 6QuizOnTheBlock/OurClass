package com.quiz.ourclass.domain.relay.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "이어달리기 전달 응답 DTO")
public record SendRelayResponse(
    @Schema(description = "이어달리기 이전 주자 이름")
    String prevMemberName,
    @Schema(description = "이어달리기 이전 주자가 받은 질문")
    String prevQuestion
) {

}
