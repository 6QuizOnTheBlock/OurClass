package com.quiz.ourclass.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberMeResponse {

    @Schema(description = "고유번호")
    private long id;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "프로필 사진")
    private String photo;
    @Schema(description = "역할")
    private String role;

}
