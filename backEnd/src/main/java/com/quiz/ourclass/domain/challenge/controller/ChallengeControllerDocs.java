package com.quiz.ourclass.domain.challenge.controller;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ReportRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import com.quiz.ourclass.domain.challenge.dto.response.RunningChallengeResponse;
import com.quiz.ourclass.domain.challenge.entity.ReportType;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ChallengeController", description = "함께달리기 API")
public interface ChallengeControllerDocs {

    @Operation(summary = "함께달리기 목록 조회",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "함께달리기 목록 조회 성공",
                content = @Content(schema = @Schema(implementation = ChallengeSliceResponse.class)))
        })
    @GetMapping
    ResponseEntity<ResultResponse<?>> getChallenges(ChallengSliceRequest challengSliceRequest);

    @Operation(summary = "함께달리기 생성",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "함께달리기 생성 성공",
                content = @Content(schema = @Schema(implementation = Long.class)))
        })
    @PostMapping
    public ResponseEntity<ResultResponse<?>> createChallenge(
        @RequestBody ChallengeRequest challengeRequest);

    @Operation(summary = "함께달리기 레포트 제출",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "함께달리기 레포트 생성 성공",
                content = @Content(schema = @Schema(implementation = Long.class)))
        })
    @PostMapping(value = "/reports", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResultResponse<?>> createReport(
        @RequestPart ReportRequest reportRequest,
        @Schema(description = "사진") @RequestPart MultipartFile file);

    @Operation(summary = "함께달리기 레포트 채점")
    @PatchMapping(value = "/reports/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse<?>> confirmReport(
        @Schema(description = "레포트 id") @PathVariable long id,
        @Schema(description = "채점값") @Parameter(description = "BEFORE,APPROVE,REFUSE") @RequestParam ReportType reportType);

    @Operation(summary = "진행중인 함께달리기 조회",
        description = "학급ID로 검색하여 해당 학급에서 진행중인 함께달리기를 조회합니다."
            + "진행중인 것이 없다면 404를 응답합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = RunningChallengeResponse.class))),
            @ApiResponse(responseCode = "404", description = "(message : \"학급을 찾을 수 없습니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"진행중인 함께달리기가 없습니다.\")", content = @Content)
        })
    @GetMapping("/running")
    public ResponseEntity<ResultResponse<?>> getRunningChallenge(
        @RequestParam(required = true)
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.QUERY)
        long organizationId
    );
}
