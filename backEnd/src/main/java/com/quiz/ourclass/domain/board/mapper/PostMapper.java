package com.quiz.ourclass.domain.board.mapper;

import com.quiz.ourclass.domain.board.dto.CommentDTO;
import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdatePostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import com.quiz.ourclass.domain.board.entity.Image;
import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.organization.entity.Organization;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(source = "post.author.id", target = "member.id")
    @Mapping(source = "post.author.profileImage", target = "member.photo")
    @Mapping(source = "post.image.path", target = "path")
    @Mapping(source = "comments", target = "comments")
    PostDetailResponse postToPostDetailResponse(Post post, List<CommentDTO> comments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", source = "createTime")
    Post postRequestToPost(PostRequest request, Member author, Organization organization,
        LocalDateTime createTime, Image image);

    void updatePostFromRequest(LocalDateTime updateTime, UpdatePostRequest request,
        @MappingTarget Post post);
}
