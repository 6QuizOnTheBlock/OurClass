package com.quiz.ourclass.domain.challenge.controller;

import com.quiz.ourclass.domain.challenge.dto.request.ChallengeRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ChallengeSliceRequest;
import com.quiz.ourclass.domain.challenge.dto.request.ReportRequest;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSimpleResponse;
import com.quiz.ourclass.domain.challenge.dto.response.ChallengeSliceResponse;
import com.quiz.ourclass.domain.challenge.dto.response.RunningChallengeResponse;
import com.quiz.ourclass.domain.challenge.dto.response.RunningMemberChallengeResponse;
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
        description = "page,size 필수로 입력받아 무한스크롤 적용.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = ChallengeSliceResponse.class)))
        })
    @GetMapping
    ResponseEntity<ResultResponse<ChallengeSliceResponse>> getChallenges(
        ChallengeSliceRequest challengeSliceRequest
    );

    @Operation(summary = "함께달리기 생성",
        description = "학생 그룹 지정 생성 시 요청데이터에 그룹 정보 포함하여 요청."
            + "자율 그룹 생성은 빈 리스트로 보내주세요.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "(message : \"학급을 찾을 수 없습니다.\")", content = @Content)
        })
    @PostMapping
    ResponseEntity<ResultResponse<Long>> createChallenge(
        @RequestBody
        ChallengeRequest challengeRequest
    );

    @Operation(summary = "함께달리기 레포트 제출",
        description = "레포트 내용DTO는 json, 사진은 MultipartFile로 받습니다",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "(message : \"그룹을 찾을 수 없습니다.\")", content = @Content),
            @ApiResponse(responseCode = "500", description = "(message : \"AWS 서버 에러입니다.\")", content = @Content)
        })
    @PostMapping(value = "/reports", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResultResponse<Long>> createReport(
        @RequestPart
        ReportRequest reportRequest,
        @RequestPart
        MultipartFile file
    );

    @Operation(summary = "함께달리기 레포트 채점",
        description = "레포트를 승인 또는 거절 합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")"),
            @ApiResponse(responseCode = "404", description = "(message : \"레포트를 찾을 수 없습니다.\")", content = @Content)
        })
    @PatchMapping(value = "/reports/{id}")
    ResponseEntity<ResultResponse<Boolean>> confirmReport(
        @Schema(description = "레포트 id")
        @PathVariable
        long id,
        @Schema(description = "채점값")
        @Parameter(description = "BEFORE,APPROVE,REFUSE", required = true, in = ParameterIn.QUERY)
        @RequestParam
        ReportType reportType
    );

    @Operation(summary = "진행중인 함께달리기 조회",
        description = "학급ID로 검색하여 해당 학급에서 진행중인 함께달리기를 조회합니다."
            + "진행중인 것이 없다면 404를 응답합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = RunningChallengeResponse.class))),
            @ApiResponse(responseCode = "404", description = """
                (message : "학급을 찾을 수 없습니다.")
                                
                (message : "진행중인 함께달리기가 없습니다.")
                """, content = @Content)
        })
    @GetMapping("/running")
    ResponseEntity<ResultResponse<RunningChallengeResponse>> getRunningChallenge(
        @RequestParam
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.QUERY)
        long organizationId
    );

    @Operation(summary = "학생용 진행중인 함께달리기 조회",
        description = """
            진행중인 것이 없다면 404를 응답합니다.
                        
                        지정된 그룹이 없으면 관련 필드가 null응답,
                        지정된 그룹이 있지만 블루투스 체킹 전은 생성시간 null응답.
            """,
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = RunningMemberChallengeResponse.class))),
            @ApiResponse(responseCode = "404", description = """
                (message : "학급을 찾을 수 없습니다.")
                                
                (message : "진행중인 함께달리기가 없습니다.")
                """, content = @Content)
        })
    @GetMapping("/running/member")
    ResponseEntity<ResultResponse<RunningMemberChallengeResponse>> getRunningMemberChallenge(
        @RequestParam
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.QUERY)
        long organizationId
    );

    @Operation(summary = "함께달리기 상세 조회",
        description = "함께달리기 ID로  검색하여 함께달리기와 관련 레포트 모두 조회합니다."
            + "그룹 ID 지정 시 해당 그룹의 레포트만 가져옵니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = ChallengeResponse.class)))
        })
    @GetMapping("/{id}")
    ResponseEntity<ResultResponse<ChallengeResponse>> getChallengeDetail(
        @PathVariable
        @Parameter(description = "함께달리기 ID", required = true, in = ParameterIn.PATH)
        long id,
        @RequestParam(required = false)
        @Parameter(description = "그룹 ID", in = ParameterIn.QUERY)
        Long groupId);

    @Operation(summary = "함께달리기 요약 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = ChallengeSimpleResponse.class)))
        })
    @GetMapping("/{id}/simple")
    ResponseEntity<ResultResponse<ChallengeSimpleResponse>> getChallengeSimple(
        @PathVariable
        @Parameter(description = "함께달리기 ID", required = true, in = ParameterIn.PATH)
        long id
    );
}
