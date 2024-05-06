package com.quiz.ourclass.domain.relay.repository;

import com.quiz.ourclass.domain.relay.dto.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.RelaySliceResponse;

public interface RelayRepositoryQuerydsl {

    RelaySliceResponse getRelays(RelaySliceRequest relaySliceRequest);
}
