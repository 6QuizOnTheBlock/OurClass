package com.quiz.ourclass.domain.board.mapper;

import com.quiz.ourclass.domain.board.dto.CommentChildrenDTO;
import com.quiz.ourclass.domain.board.dto.CommentDTO;
import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdateCommentRequest;
import com.quiz.ourclass.domain.board.entity.Comment;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "member.profileImage", target = "member.photo")
    CommentChildrenDTO commentToCommentChildrenDTO(Comment comment);

    @Mapping(source = "comment.member.profileImage", target = "member.photo")
    CommentDTO commentToCommentDTOWithChildren(Comment comment, List<CommentChildrenDTO> children);

    @Mapping(source = "boardId", target = "post.id")
    Comment commentRequestTocomment(CommentRequest commentRequest);

    void updateCommentFromRequest(UpdateCommentRequest request, @MappingTarget Comment comment);

}
