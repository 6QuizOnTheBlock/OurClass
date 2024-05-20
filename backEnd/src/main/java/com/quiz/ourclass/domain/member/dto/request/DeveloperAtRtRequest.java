package com.quiz.ourclass.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DeveloperAtRtRequest {

    @Schema(description = "개발자 이메일")
    private String email;
}
