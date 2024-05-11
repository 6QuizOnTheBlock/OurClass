package com.quiz.ourclass.domain.relay.dto.request;

public record ReceiveRelayRequest(
    long senderId,
    String question
) {

}
