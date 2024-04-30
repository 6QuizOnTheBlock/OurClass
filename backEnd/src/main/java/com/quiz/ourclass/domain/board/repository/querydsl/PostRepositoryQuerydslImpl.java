package com.quiz.ourclass.domain.board.repository.querydsl;

import static com.quiz.ourclass.domain.board.entity.QComment.comment;
import static com.quiz.ourclass.domain.board.entity.QPost.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.board.entity.Comment;
import com.quiz.ourclass.domain.board.entity.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryQuerydslImpl implements PostRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public Post fetchPostWithDetails(Long postId) {
        return queryFactory
            .selectFrom(post)
            .leftJoin(post.image).fetchJoin()
            .leftJoin(post.comments, comment).fetchJoin()
            .where(post.id.eq(postId))
            .fetchOne();
    }

    public List<Comment> fetchCommentsByPostId(Long postId) {
        return queryFactory
            .selectFrom(comment)
            .where(comment.post.id.eq(postId))
            .fetch();
    }
}
