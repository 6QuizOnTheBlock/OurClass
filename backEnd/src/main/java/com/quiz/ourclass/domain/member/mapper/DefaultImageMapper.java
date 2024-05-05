package com.quiz.ourclass.domain.member.mapper;

import com.quiz.ourclass.domain.member.dto.response.DefaultImagesResponse;
import com.quiz.ourclass.domain.member.entity.DefaultImage;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DefaultImageMapper {


    // 매핑이 까다로운 경우 앞에 default 예약어를 붙인 메소드를 만들어서 사용하면 된다.
    // 그러면 사용자 정의 매소드로 사용할 수 있다.
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
}
