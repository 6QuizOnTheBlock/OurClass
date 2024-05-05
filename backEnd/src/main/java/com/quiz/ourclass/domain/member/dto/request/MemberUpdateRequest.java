package com.quiz.ourclass.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder

public record MemberUpdateRequest(
    @Schema(description = "바꿀 사진")
    MultipartFile file
) {

}
