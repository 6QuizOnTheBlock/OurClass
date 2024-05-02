package com.quiz.ourclass.domain.board.controller;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdateCommentRequest;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "CommentController", description = "SNS 댓글 API")
public interface CommentControllerDocs {

    @Operation(summary = "댓글 작성",
        description = """
            입력으로 들어오는 DTO 기준으로 댓글을 작성합니다.
            이때, 부모 댓글은 parentId 값에 0L을 보내주세요!""",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "403", description = """
                (message : "멤버가 해당 단체 소속이 아닙니다.")
                                
                (message : "게시글 작성자 단체와 현재 사용자 단체가 다릅니다.")
                """, content = @Content),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                                
                (message : "게시글을 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @PostMapping
    ResponseEntity<ResultResponse<Long>> write(
        @Parameter(name = "request", description = "댓글 작성 DTO", required = true, in = ParameterIn.DEFAULT)
        @RequestBody CommentRequest request
    );

    @Operation(summary = "댓글 수정",
        description = "입력으로 들어오는 DTO 기준으로 댓글을 수정합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"댓글 작성자 단체와 현재 사용자 단체가 다릅니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                                
                (message : "댓글을 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @PatchMapping("/{id}")
    ResponseEntity<ResultResponse<Long>> modify(
        @Parameter(name = "id", description = "댓글 PK 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "id") Long id,
        @Parameter(name = "request", description = "댓글 수정 DTO", required = true, in = ParameterIn.DEFAULT)
        @RequestBody UpdateCommentRequest request
    );

    @Operation(summary = "댓글 삭제",
        description = "입력으로 들어오는 댓글 PK 기준으로 댓글을 삭제합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "403", description = """
                (message : "댓글 작성자와 요청자가 다릅니다.")
                                
                (message : "멤버가 해당 단체 소속이 아닙니다.")
                """, content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"댓글을 찾을 수 없습니다.\")", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<ResultResponse<Boolean>> delete(
        @Parameter(name = "id", description = "댓글 PK 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "id") Long id
    );
}
