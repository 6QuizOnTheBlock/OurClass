package com.quiz.ourclass.domain.chat.controller;

import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;
import com.quiz.ourclass.domain.chat.dto.request.ChatFilterSliceRequest;
import com.quiz.ourclass.domain.chat.dto.response.ChatFilterResponse;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ChatFilterControllerDocs {

    @Operation(summary = "채팅 필터링 단어 등록",
        description = "입력으로 들어온 단어를 필터링 단어에 등록합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = """        
                (message : "이미 등록된 단어가 있습니다.")
                """, content = @Content),
            @ApiResponse(responseCode = "404", description = """                
                (message : "단체를 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @PostMapping("/{organizationId}")
    ResponseEntity<ResultResponse<Long>> register(
        @Parameter(name = "organizationId", description = "단체 ID 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "organizationId") Long organizationId,
        @Parameter(name = "request", description = "필터링 단어 DTO", required = true, in = ParameterIn.DEFAULT)
        @RequestBody ChatFilterRequest request
    );

    @Operation(summary = "채팅 필터링 단어 수정",
        description = "입력으로 들어온 단어를 필터링 단어 PK에 맞게 수정합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = """        
                (message : "이미 등록된 단어가 있습니다.")
                """, content = @Content),
            @ApiResponse(responseCode = "404", description = """        
                (message : "단어를 찾을 수 없습니다.")
                                
                (message : "단체를 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @PatchMapping("/{organizationId}/{id}")
    ResponseEntity<ResultResponse<Boolean>> modify(
        @Parameter(name = "organizationId", description = "단체 ID", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "organizationId") Long organizationId,
        @Parameter(name = "id", description = "단어 필터링 ID 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "id") Long id,
        @Parameter(name = "request", description = "수정할 단어", required = true, in = ParameterIn.DEFAULT)
        @RequestBody ChatFilterRequest request
    );

    @Operation(summary = "채팅 필터링 단어 삭제",
        description = "입력으로 들어온 단어를 필터링 단어 PK에 맞게 삭제합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = """
                (message : "단어를 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ResultResponse<Boolean>> delete(
        @Parameter(name = "id", description = "단어 필터링 ID 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "id") Long chatFilterId
    );

    @Operation(summary = "채팅 필터링 단어 조회",
        description = "쿼리 입력으로 들어온 값 기준으로 채팅 필터링 단어를 조회합니다. (page 0부터 시작입니다!) (size 1부터 시작입니다!)",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = ChatFilterResponse.class))),
            @ApiResponse(responseCode = "400", description = """
                (message : "잘못된 요청입니다.")
                """, content = @Content),
            @ApiResponse(responseCode = "404", description = """                
                (message : "단체를 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @GetMapping
    ResponseEntity<ResultResponse<ChatFilterResponse>> select(
        ChatFilterSliceRequest request
    );
}
