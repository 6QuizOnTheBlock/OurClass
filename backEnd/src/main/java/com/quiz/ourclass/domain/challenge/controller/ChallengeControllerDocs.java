package com.quiz.ourclass.domain.challenge.controller;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "ChallengeController", description = "함께달리기 API")
public interface ChallengeControllerDocs {

    @Operation(summary = "함께달리기 목록 조회",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                description = "함께달리기 목록 조회 성공",
                content = @Content(schema = @Schema(implementation = ChallengeSliceResponse.class)))
        })
    @GetMapping
    ResponseEntity<ResultResponse<?>> getChallenges(ChallengSliceRequest challengSliceRequest);

    @Operation(summary = "함께달리기 생성",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                description = "함께달리기 생성 성공",
                content = @Content(schema = @Schema(implementation = Long.class)))
        })
    @PostMapping
    public ResponseEntity<ResultResponse<?>> createChallenge(
        @RequestBody ChallengeRequest challengeRequest);
}
