package com.quiz.ourclass.domain.board.repository.querydsl;

import static com.quiz.ourclass.domain.board.entity.QComment.comment;
import static com.quiz.ourclass.domain.board.entity.QPost.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiz.ourclass.domain.board.dto.PostListDTO;
import com.quiz.ourclass.domain.board.dto.request.PostSliceRequest;
import com.quiz.ourclass.domain.board.dto.response.PostListResponse;
import com.quiz.ourclass.domain.board.entity.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @Override
    public PostListResponse getPostList(PostSliceRequest request) {
        Pageable pageable = PageRequest.of(request.page(), request.size());
        BooleanBuilder postCondition = getPostsBooleanBuilder(request);

        List<PostListDTO> posts = queryFactory
            .select(Projections.constructor(PostListDTO.class,
                post.id,
                post.title,
                post.author.name.as("author"),
                post.createTime,
                Expressions.numberTemplate(Long.class, "count(comment.id)").as("commentCount")
            ))
            .from(post)
            .leftJoin(post.comments, comment)
            .groupBy(post.id)
            .where(postCondition)
            .limit(pageable.getPageSize() + 1)
            .offset(pageable.getOffset())
            .orderBy(post.id.desc())
            .fetch();

        boolean hasNext = false;
        if (posts.size() > pageable.getPageSize()) {
            posts.remove(pageable.getPageSize());
            hasNext = true;
        }
        return PostListResponse.builder()
            .page(request.page())
            .size(request.size())
            .hasNextPage(hasNext)
            .posts(posts)
            .build();
    }

    private static BooleanBuilder getPostsBooleanBuilder(PostSliceRequest request) {
        BooleanBuilder postCondition = new BooleanBuilder();
        // 해당 단체의 게시글 조회 필터
        if (request.organizationId() != null && request.organizationId() > 0) {
            postCondition.and(post.organization.id.eq(request.organizationId()));
            postCondition.and(post.postCategory.eq(request.postCategory()));
        }
        if (request.memberId() != null && request.memberId() > 0) {
            // memberId가 0보다 크면 해당 멤버가 작성한 게시글만 조회
            postCondition.and(post.author.id.eq(request.memberId()));
        }
        return postCondition;
    }
}
