package com.quiz.ourclass.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberSimpleDTO(
    @Schema(description = "사용자 id", example = "99")
    long id,
    @Schema(description = "사용자 이름", example = "차성원")
    String name,
    @Schema(description = "사용자 프로필 사진", example = "www.photo.com")
    String photo
) {

}
