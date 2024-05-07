package com.quiz.ourclass.domain.relay.service;

import com.quiz.ourclass.domain.relay.dto.request.RelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.response.RelaySliceResponse;

public interface RelayService {

    long createRelay(RelayRequest relayRequest);

    RelaySliceResponse getRelays(RelaySliceRequest relaySliceRequest);
}
