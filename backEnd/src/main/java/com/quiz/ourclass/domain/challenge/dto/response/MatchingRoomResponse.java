package com.quiz.ourclass.domain.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "그룹 매칭대기방 생성 응답 DTO")
public record MatchingRoomResponse(
    @Schema(description = "그룹 매칭대기방 id")
    String dataKey,
    @Schema(description = "그룹 최소인원")
    int minCount
) {

}
