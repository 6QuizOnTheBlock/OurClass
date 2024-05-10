package com.quiz.ourclass.domain.chat.controller;

import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<ResultResponse<?>> register(
        @Parameter(name = "organizationId", description = "단체 ID 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "organizationId") Long organizationId,
        @Parameter(name = "request", description = "필터링 단어 DTO", required = true, in = ParameterIn.DEFAULT)
        @RequestBody ChatFilterRequest request
    );

    @Operation(summary = "채팅 필터링 단어 수정",
        description = "입력으로 들어온 단어를 필터링 단어 PK에 맞게 수정합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = """        
                (message : "이미 등록된 단어가 있습니다.")
                """, content = @Content),
            @ApiResponse(responseCode = "404", description = """        
                (message : "단어를 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @PatchMapping("/{chatFilterId}")
    ResponseEntity<ResultResponse<?>> modify(
        @Parameter(name = "chatFilterId", description = "단어 필터링 ID 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "chatFilterId") Long chatFilterId,
        @Parameter(name = "request", description = "수정할 단어", required = true, in = ParameterIn.DEFAULT)
        @RequestBody ChatFilterRequest request
    );
}
