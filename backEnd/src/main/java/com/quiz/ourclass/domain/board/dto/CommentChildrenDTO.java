package com.quiz.ourclass.domain.board.dto;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "대댓글 정보")
public record CommentChildrenDTO(
    @Schema(description = "자식 댓글 PK", example = "2")
    Long id,
    @Schema(description = "자식 댓글 작성자 정보")
    MemberSimpleDTO member,
    @Schema(description = "자식 댓글 내용", example = "아..나도 속았다ㅠㅠ 우리끼리 가실?")
    String content,
    @Schema(description = "자식 댓글 작성 시간", example = "2024-04-29T14:30:00")
    LocalDateTime createTime,
    @Schema(description = "자식 댓글 수정 시간", example = "2024-04-30T21:30:00")
    LocalDateTime updateTime,
    @Schema(description = "부모 댓글 PK")
    Long parentId
) {

}
