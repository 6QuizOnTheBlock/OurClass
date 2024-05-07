package com.quiz.ourclass.domain.relay.controller;

import com.quiz.ourclass.domain.relay.dto.request.RelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.response.RelaySliceResponse;
import com.quiz.ourclass.domain.relay.service.RelayService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/relays")
@RestController
public class RelayController implements RelayControllerDocs {

    private final RelayService relayService;

    @PostMapping()
    public ResponseEntity<ResultResponse<?>> createRelay(@RequestBody RelayRequest relayRequest) {
        long relayId = relayService.createRelay(relayRequest);
        return ResponseEntity.ok(ResultResponse.success(relayId));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<?>> getRelays(RelaySliceRequest relaySliceRequest) {
        RelaySliceResponse relays = relayService.getRelays(relaySliceRequest);
        return ResponseEntity.ok(ResultResponse.success(relays));
    }
}
