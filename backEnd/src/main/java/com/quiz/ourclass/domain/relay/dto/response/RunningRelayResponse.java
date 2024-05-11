package com.quiz.ourclass.domain.relay.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
@Schema(description = "진행중인 이어달리기 조회 응답 DTO")
public record RunningRelayResponse(
    @Schema(description = "이어달리기 id")
    long id,
    @Schema(description = "이어달리기 시작 시간")
    LocalDateTime startTime,
    @Schema(description = "이어달리기 현재 주자 이름")
    String currentMemberName,
    @Schema(description = "이어달리기 현재 주자 턴")
    int currentTurn,
    @Schema(description = "이어달리기 전체 턴 수")
    int totalTurn,
    @Schema(description = "내 차례 여부")
    boolean myTurnStatus
) {

}
