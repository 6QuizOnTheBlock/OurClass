package com.quiz.ourclass.domain.member.mapper;

import com.quiz.ourclass.domain.board.mapper.CommentMapper;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    @Mapping(source = "profileImage", target = "photo")
    MemberSimpleDTO memberToMemberSimpleDTO(Member member);
}
