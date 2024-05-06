package com.quiz.ourclass.domain.relay.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "이어달리기 DTO")
public record RelaySimpleDTO(
    @Schema(description = "이어달리기 ID")
    long id,
    @Schema(description = "이어달리기 시작 시간")
    LocalDateTime startTime,
    @Schema(description = "이어달리기 종료 시간")
    LocalDateTime endTime,
    @Schema(description = "릴레이 종료 턴 수")
    int lastTurn,
    @Schema(description = "마지막 주자 이름")
    String lastMemberName
) {

}
