package com.quiz.ourclass.domain.board.repository.querydsl;

import com.quiz.ourclass.domain.board.entity.Comment;
import com.quiz.ourclass.domain.board.entity.Post;
import java.util.List;

public interface PostRepositoryQuerydsl {

    Post fetchPostWithDetails(Long postId);

    List<Comment> fetchCommentsByPostId(Long postId);
}
