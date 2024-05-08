package com.quiz.ourclass.domain.relay.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record RunningRelayResponse(
    long id,
    LocalDateTime startTime,
    String currentMemberName,
    int currentTurn,
    boolean myTurnStatus
) {

}
