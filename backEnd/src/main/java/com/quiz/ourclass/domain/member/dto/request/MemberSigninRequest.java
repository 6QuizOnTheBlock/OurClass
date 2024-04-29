package com.quiz.ourclass.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSigninRequest {

    private String email;


    private MemberSigninRequest (String email) {
        this.email = email;
    }


    public static MemberSigninRequest of (String email) {
        return builder().email(email).build();
    }
}
