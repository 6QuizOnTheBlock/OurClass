package com.quiz.ourclass.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpRequest {

    @Schema(description = "회원 식별 ID 토큰")
    private String idToken;

    @Schema(description = "회원 프로필 사진")
    private MultipartFile file;

    @Schema(description = "회원의 직책")
    private String role;

    @Schema(description = "기본 이미지 번호")
    private int defaultImage;

}
