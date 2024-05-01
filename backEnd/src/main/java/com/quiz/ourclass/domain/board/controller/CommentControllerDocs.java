package com.quiz.ourclass.domain.board.controller;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "CommentController", description = "SNS 댓글 API")
public interface CommentControllerDocs {

    @Operation(summary = "댓글 작성",
        description = "입력으로 들어오는 DTO 기준으로 댓글을 작성합니다. "
            + "\n이때, 부모 댓글은 parentId 값에 0L을 보내주세요!",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"멤버가 해당 단체 소속이 아닙니다.\")", content = @Content),
            @ApiResponse(responseCode = "403", description = "(message : \"게시글 작성자 단체와 현재 사용자 단체가 다릅니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"멤버가 존재하지 않습니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"게시글을 찾을 수 없습니다.\")", content = @Content)
        }
    )
    @PostMapping
    ResponseEntity<ResultResponse<Long>> write(
        @Parameter(name = "request", description = "댓글 작성 DTO", required = true, in = ParameterIn.DEFAULT)
        @RequestBody CommentRequest request);
}
