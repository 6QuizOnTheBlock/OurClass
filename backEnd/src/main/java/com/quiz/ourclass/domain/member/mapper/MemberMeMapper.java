package com.quiz.ourclass.domain.member.mapper;

import com.quiz.ourclass.domain.member.dto.response.MemberMeResponse;
import com.quiz.ourclass.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/*
 * @Mapper 어노테이션 붙이면, MapStruct 가 자동으로 MemberMeMapper 의 구현체를 생성해준다.
 * */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMeMapper {

    // 1) 실제 구현된 MemberMeMapper 클래스의 객체를 가져오는 함수 -> static 함수도 똑같음.
    MemberMeMapper INSTANCE = Mappers.getMapper(MemberMeMapper.class);

    // 2) Mapping 진행 Entity -> DTO
    // 이름이 다른 것은 타겟지정
    @Mapping(source = "member.profileImage", target = "photo")
    MemberMeResponse toMemberMeResponse(Member member);

}
