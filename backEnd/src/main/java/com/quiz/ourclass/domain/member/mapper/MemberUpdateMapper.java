package com.quiz.ourclass.domain.member.mapper;

import com.quiz.ourclass.domain.member.dto.response.MemberUpdateResponse;
import com.quiz.ourclass.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberUpdateMapper {

    @Mapping(source = "member.profileImage", target = "photoImageUrl")
    MemberUpdateResponse toUpdateResponse(Member member);
}
