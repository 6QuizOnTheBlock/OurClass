package com.quiz.ourclass.domain.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "대댓글 정보")
public record CommentChildrenDTO(
    @Schema(description = "자식 댓글 PK", example = "2")
    String commentId,
    @Schema(description = "자식 댓글 작성자", example = "정철주")
    String memberName,
    @Schema(description = "자식 댓글 내용", example = "아..나도 속았다ㅠㅠ 우리끼리 가실?")
    String commentContent,
    @Schema(description = "자식 댓글 작성 시간", example = "2024-04-29T14:30:00")
    LocalDateTime time,
    @Schema(description = "부모 댓글 PK")
    Integer commentParentId
) {

}
