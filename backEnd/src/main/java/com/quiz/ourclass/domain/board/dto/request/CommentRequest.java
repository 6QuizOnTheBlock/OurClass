package com.quiz.ourclass.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 작성 요청 DTO")
public record CommentRequest(
    @Schema(description = "게시글 ID", example = "1")
    Long boardId,
    @Schema(description = "게시글 내용", example = "오하빈 짱!!")
    String content,
    @Schema(description = "댓글 부모 ID", example = "0")
    Long parentId
) {

}
