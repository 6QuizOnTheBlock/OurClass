package com.quiz.ourclass.domain.relay.controller;

import com.quiz.ourclass.domain.relay.dto.request.ReceiveRelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelayRequest;
import com.quiz.ourclass.domain.relay.dto.request.RelaySliceRequest;
import com.quiz.ourclass.domain.relay.dto.response.ReceiveRelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.RelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.RelaySliceResponse;
import com.quiz.ourclass.domain.relay.dto.response.RunningRelayResponse;
import com.quiz.ourclass.domain.relay.dto.response.SendRelayResponse;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "RelayController", description = "이어달리기 API")
public interface RelayControllerDocs {

    @Operation(summary = "이어달리기 생성",
        description = "진행중인 이어달리기 없을 때 생성가능. "
            + "이어달리기 총 진행 횟수는 학급멤버 전체수~절반 사이 랜덤. 타임아웃 1일",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = """
                (message : "학급을 찾을 수 없습니다.")
                                
                (message : "멤버가 존재하지 않습니다.")
                """, content = @Content)
        })
    @PostMapping
    ResponseEntity<ResultResponse<Long>> createRelay(
        @RequestBody
        RelayRequest relayRequest
    );

    @Operation(summary = "이어달리기 목록 조회",
        description = "진행중인 이어달리기는 제외. 페이징 size 필수 ",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = RelaySliceRequest.class)))
        })
    @GetMapping
    ResponseEntity<ResultResponse<RelaySliceResponse>> getRelays(RelaySliceRequest relaySliceRequest
    );

    @Operation(summary = "이어달리기 상세 조회",
        description = "릴레이 참여 주자 내역까지 같이 응답 ",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = RelayResponse.class)))
        })
    @GetMapping("/{id}")
    ResponseEntity<ResultResponse<RelayResponse>> getRelayDetail(
        @PathVariable
        @Parameter(description = "이어달리기 id", required = true, in = ParameterIn.PATH)
        long id
    );

    @Operation(summary = "현재 진행중인 이어달리기 조회",
        description = "진행중인 이어달리기 없을 때는 404. ",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = RunningRelayResponse.class))),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                                
                (message : "진행중인 이어달리기가 없습니다.")
                """, content = @Content)
        })
    @GetMapping("/running")
    ResponseEntity<ResultResponse<RunningRelayResponse>> getRunningRelay(
        @RequestParam
        @Parameter(description = "학급 id", required = true, in = ParameterIn.QUERY)
        long organizationId
    );

    @Operation(summary = "이어달리기 전달 받기",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = ReceiveRelayResponse.class))),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                                
                (message : "이어달리기를 찾을 수 없습니다.")
                """, content = @Content)
        })
    @PostMapping("/{id}/receive")
    ResponseEntity<ResultResponse<ReceiveRelayResponse>> receiveRelay(
        @PathVariable
        @Parameter(description = "이어달리기 id", required = true, in = ParameterIn.PATH)
        long id,
        @RequestBody
        ReceiveRelayRequest receiveRelayRequest
    );

    @Operation(summary = "이어달리기 전달",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = SendRelayResponse.class))),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                                
                (message : "이어달리기를 찾을 수 없습니다.")
                                
                (message : "이어달리기 주자를 찾을 수 없습니다.")
                """, content = @Content)
        })
    @PostMapping("/{id}/send")
    ResponseEntity<ResultResponse<SendRelayResponse>> sendRelay(
        @PathVariable
        @Parameter(description = "이어달리기 id", required = true, in = ParameterIn.PATH)
        long id
    );

    @Operation(summary = "이어달리기 받은 질문 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                                
                (message : "이어달리기를 찾을 수 없습니다.")
                                
                (message : "이어달리기 주자를 찾을 수 없습니다.")
                """, content = @Content)
        })
    @GetMapping("/{id}/question")
    ResponseEntity<ResultResponse<String>> getRelayQuestion(
        @PathVariable
        long id
    );
}
