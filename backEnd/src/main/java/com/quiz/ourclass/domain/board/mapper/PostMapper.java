package com.quiz.ourclass.domain.board.mapper;

import com.quiz.ourclass.domain.board.dto.CommentDTO;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import com.quiz.ourclass.domain.board.entity.Post;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(source = "post.author.id", target = "member.id")
    @Mapping(source = "post.image.path", target = "path")
    @Mapping(source = "comments", target = "comments")
    PostDetailResponse postToPostDetailResponse(Post post, List<CommentDTO> comments);
}
