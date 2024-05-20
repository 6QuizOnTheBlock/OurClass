package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdateCommentRequest;

public interface CommentService {

    Long write(CommentRequest request);

    Long modify(Long commentId, UpdateCommentRequest request);

    Boolean delete(Long commentId);
    
    Boolean report(Long commentId);
}
