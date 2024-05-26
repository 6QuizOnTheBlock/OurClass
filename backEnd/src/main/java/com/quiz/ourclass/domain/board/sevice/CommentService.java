package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdateCommentRequest;

public interface CommentService {

    Long commentWrite(CommentRequest request);

    Long commentModify(Long commentId, UpdateCommentRequest request);

    Boolean commentDelete(Long commentId);

    Boolean commentReport(Long commentId);
}
