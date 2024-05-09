package com.quiz.ourclass.domain.chat.controller;

import com.quiz.ourclass.domain.chat.dto.request.MessageSliceRequest;
import com.quiz.ourclass.domain.chat.dto.response.MessageResponse;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ChatControllerDocs {

    @Operation(summary = "채팅방 나가기 (퇴장 하기)",
        description = """
            채팅방 ID를 기준으로 해당 유저를 채팅방 입장 기록에서 지웁니다.
            """,
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")"),
            @ApiResponse(responseCode = "403", description = """
                (message : "멤버가 해당 단체 소속이 아닙니다.")
                """, content = @Content),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                                
                (message : "채팅방을 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @PatchMapping("/room/{id}")
    void exitChatRoom(
        @Parameter(name = "id", description = "채팅방 ID 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "id") Long id
    );

    @Operation(summary = "채팅 메시지 조회",
        description = "쿼리 입력으로 들어온 값 기준으로 채팅 메시지를 조회합니다. (page 0부터 시작입니다!) (size 1부터 시작입니다!)",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = """
                (message : "잘못된 요청입니다.")
                """, content = @Content),
            @ApiResponse(responseCode = "404", description = """                
                (message : "채팅방을 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @GetMapping
    ResponseEntity<ResultResponse<?>> chatListView(
        MessageSliceRequest request
    );
}
