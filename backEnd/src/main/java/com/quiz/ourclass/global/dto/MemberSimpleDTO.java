package com.quiz.ourclass.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberSimpleDTO(
    @Schema(description = "자식 댓글 작성자 정보", example = "99")
    long id,
    @Schema(description = "자식 댓글 작성자 정보", example = "차성원")
    String name,
    @Schema(description = "자식 댓글 작성자 정보", example = "www.photo.com")
) {

}
