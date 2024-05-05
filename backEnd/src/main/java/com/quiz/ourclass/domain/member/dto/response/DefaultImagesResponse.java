package com.quiz.ourclass.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;


@Builder
@Schema(description = "기본 이미지 조회 DTO")
public record DefaultImagesResponse(
    @Schema(description = "남학생 이미지")
    String studentManImage,
    @Schema(description = "여학생 이미지")
    String studentWomanImage,
    @Schema(description = "남교사 이미지")
    String teacherManImage,
    @Schema(description = "여교사 이미지")
    String teacherWomanImage
) {

}
