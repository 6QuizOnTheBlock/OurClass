package com.quiz.ourclass.domain.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "댓글 정보 DTO")
public record CommentDTO(
    @Schema(description = "부모 댓글 PK", example = "1")
    Long commentId,
    @Schema(description = "부모 댓글 작성자", example = "홍유준")
    String memberName,
    @Schema(description = "부모 댓글 내용", example = "아 또 속았네ㅠㅠ")
    String commentContent,
    @Schema(description = "댓글 작성 시간", example = "2024-04-29T14:00:00")
    LocalDateTime time,
    @Schema(description = "대댓글 정보")
    List<CommentChildrenDTO> children
) {

}
