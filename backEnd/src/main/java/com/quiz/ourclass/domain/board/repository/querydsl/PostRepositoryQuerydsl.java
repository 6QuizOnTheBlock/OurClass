package com.quiz.ourclass.domain.board.repository.querydsl;

import com.quiz.ourclass.domain.board.entity.Post;

public interface PostRepositoryQuerydsl {

    Post fetchPostWithDetails(Long postId);

}
