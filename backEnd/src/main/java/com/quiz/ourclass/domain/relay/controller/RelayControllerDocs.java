package com.quiz.ourclass.domain.relay.controller;

import com.quiz.ourclass.domain.relay.dto.RelayRequest;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "ChallengeController", description = "함께달리기 API")
public interface RelayControllerDocs {

    @Operation(summary = "이어달리기 생성",
        description = "진행중인 이어달리기 없을 때 생성가능. "
            + "이어달리기 총 진행 횟수는 학급멤버 전체수~절반 사이 랜덤. 타임아웃 1일",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "(message : \"학급을 찾을 수 없습니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"멤버가 존재하지 않습니다.\")", content = @Content)
        })
    @PostMapping()
    ResponseEntity<ResultResponse<?>> createRelay(
        @RequestBody
        RelayRequest relayRequest
    );
}
