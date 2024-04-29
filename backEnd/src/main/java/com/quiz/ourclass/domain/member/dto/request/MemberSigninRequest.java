package com.quiz.ourclass.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSigninRequest {

    private String email;

    @Builder
    private MemberSigninRequest (String email) {
        this.email = email;
    }


    public static MemberSigninRequest of (String email) {
        return builder().email(email).build();
    }
}
