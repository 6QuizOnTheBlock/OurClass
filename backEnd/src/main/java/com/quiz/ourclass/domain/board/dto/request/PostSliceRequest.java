package com.quiz.ourclass.domain.board.dto.request;

import com.quiz.ourclass.domain.board.entity.PostCategory;
import io.swagger.v3.oas.annotations.media.Schema;

public record PostSliceRequest(
    @Schema(description = "단체 PK", example = "1")
    Long organizationId,
    @Schema(description = "멤버 PK")
    Long memberId,
    @Schema(description = "페이지 (0부터 시작)", example = "0")
    Integer page,
    @Schema(description = "한 페이지 당 사이즈", example = "3")
    Integer size,
    @Schema(description = "게시글 카테고리", example = "FREE")
    PostCategory postCategory
) {

}
