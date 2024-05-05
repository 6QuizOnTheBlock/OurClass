package com.quiz.ourclass.domain.member.mapper;

import com.quiz.ourclass.domain.member.dto.response.DefaultImagesResponse;
import com.quiz.ourclass.domain.member.dto.response.MemberMeResponse;
import com.quiz.ourclass.domain.member.dto.response.MemberUpdateResponse;
import com.quiz.ourclass.domain.member.entity.DefaultImage;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/*
 * @Mapper 어노테이션 붙이면, MapStruct 가 자동으로 MemberMeMapper 의 구현체를 생성해준다.
 * */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {


    default DefaultImagesResponse toDefaultImages(List<DefaultImage> images) {
        if (images == null || images.size() < 4) {
            throw new GlobalException(ErrorCode.DEFAULT_IMAGES_UNDER_4);
        }

        return DefaultImagesResponse.builder()
            .studentManImage(images.get(0).getPhoto())
            .studentWomanImage(images.get(1).getPhoto())
            .teacherManImage(images.get(2).getPhoto())
            .teacherWomanImage(images.get(3).getPhoto())
            .build();
    }

    // 2) Mapping 진행 Entity -> DTO
    // 이름이 다른 것은 타겟지정
    @Mapping(source = "member.profileImage", target = "photo")
    MemberMeResponse toMemberMeResponse(Member member);

    @Mapping(source = "member.profileImage", target = "photoImageUrl")
    MemberUpdateResponse toUpdateResponse(Member member);
}
