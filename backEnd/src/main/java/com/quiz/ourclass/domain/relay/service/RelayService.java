package com.quiz.ourclass.domain.relay.service;

import com.quiz.ourclass.domain.relay.dto.RelayRequest;
import com.quiz.ourclass.domain.relay.dto.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.RelaySliceResponse;

public interface RelayService {

    long createRelay(RelayRequest relayRequest);

    RelaySliceResponse getRelays(RelaySliceRequest relaySliceRequest);
}
