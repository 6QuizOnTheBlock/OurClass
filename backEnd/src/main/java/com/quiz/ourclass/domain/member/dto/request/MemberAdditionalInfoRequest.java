package com.quiz.ourclass.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class MemberAdditionalInfoRequest {

    @Schema(description = "회원 프로필 사진")
    private MultipartFile file;

    @Schema(description = "회원의 직책")
    private String role;


    private MemberAdditionalInfoRequest (MultipartFile file, String role){
        this.file = file;
        this.role = role;
    }

    public  static MemberAdditionalInfoRequest of(MultipartFile file, String role){
        return builder().file(file).role(role).build();
    }
}
