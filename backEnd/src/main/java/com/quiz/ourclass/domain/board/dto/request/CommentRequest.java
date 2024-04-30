package com.quiz.ourclass.domain.board.dto.request;

public record CommentRequest(
    Long boardId,
    String content,
    Long parentId
) {

}
