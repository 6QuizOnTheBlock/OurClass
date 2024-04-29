package com.quiz.ourclass.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class MemberAdditionalInfoRequest {
    private MultipartFile file;
    private String role;


    private MemberAdditionalInfoRequest (MultipartFile file, String role){
        this.file = file;
        this.role = role;
    }

    public  static MemberAdditionalInfoRequest of(MultipartFile file, String role){
        return builder().file(file).role(role).build();
    }
}
