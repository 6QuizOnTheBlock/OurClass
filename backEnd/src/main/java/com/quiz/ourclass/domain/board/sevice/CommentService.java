package com.quiz.ourclass.domain.board.sevice;

import com.quiz.ourclass.domain.board.dto.request.CommentRequest;

public interface CommentService {

    Long write(CommentRequest request);
}
