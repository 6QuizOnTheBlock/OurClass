package com.quiz.ourclass.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateCommentRequest(
    @Schema(description = "댓글 ID", example = "5")
    Long id,
    @Schema(description = "댓글 내용", example = "빠라삐리뽀~!!!!")
    String content
) {

}
