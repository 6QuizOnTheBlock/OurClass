package com.quiz.ourclass.domain.relay.dto.response;

import lombok.Builder;

@Builder
public record ReceiveRelayResponse(
    String senderName,
    String question,
    boolean lastStatus,
    int demerit
) {

}
