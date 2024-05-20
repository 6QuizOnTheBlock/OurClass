package com.quiz.ourclass.domain.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "게시글 목록 리스트 정보")
public record PostListDTO(
    @Schema(description = "게시글 PK", example = "1")
    Long id,
    @Schema(description = "게시글 제목", example = "오늘은 뭐 할래?")
    String title,
    @Schema(description = "게시글 작성자", example = "정철주")
    String author,
    @Schema(description = "게시글 작성 시간", example = "2024-04-29T13:00:00")
    LocalDateTime createTime,
    @Schema(description = "댓글 개수", example = "6")
    Long CommentCount
) {

}
