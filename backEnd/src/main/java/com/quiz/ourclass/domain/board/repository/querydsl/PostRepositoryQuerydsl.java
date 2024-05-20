package com.quiz.ourclass.domain.board.repository.querydsl;

import com.quiz.ourclass.domain.board.dto.request.PostSliceRequest;
import com.quiz.ourclass.domain.board.dto.response.PostListResponse;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.organization.dto.response.MemberPlayCountResponse;
import java.util.List;

public interface PostRepositoryQuerydsl {

    Post fetchPostWithDetails(Long postId);

    PostListResponse getPostList(PostSliceRequest request);

    List<MemberPlayCountResponse> countPostsByOrganizationIdGroupByMember(Long organizationId);
}
