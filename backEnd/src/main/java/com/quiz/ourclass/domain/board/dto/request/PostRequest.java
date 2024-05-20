package com.quiz.ourclass.domain.board.dto.request;

import com.quiz.ourclass.domain.board.entity.PostCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 작성, 수정 요청 DTO")
public record PostRequest(
    @Schema(description = "게시글 제목", example = "ㅈㄱㄴ")
    String title,
    @Schema(description = "게시글 내용", example = "오늘 나랑 PC방 갈 사람?")
    String content,
    @Schema(description = "익명 여부", example = "true")
    Boolean secretStatus,
    @Schema(description = "게시글 카테고리", example = "FREE")
    PostCategory postCategory
) {

}
