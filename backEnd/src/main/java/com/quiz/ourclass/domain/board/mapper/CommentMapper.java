package com.quiz.ourclass.domain.board.mapper;

import com.quiz.ourclass.domain.board.dto.CommentChildrenDTO;
import com.quiz.ourclass.domain.board.dto.CommentDTO;
import com.quiz.ourclass.domain.board.dto.request.CommentRequest;
import com.quiz.ourclass.domain.board.entity.Comment;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentChildrenDTO commentToCommentChildrenDTO(Comment comment);

    CommentDTO commentToCommentDTOWithChildren(Comment comment, List<CommentChildrenDTO> children);

    @Mapping(source = "boardId", target = "post.id")
    Comment CommentRequestTocomment(CommentRequest commentRequest);

}
