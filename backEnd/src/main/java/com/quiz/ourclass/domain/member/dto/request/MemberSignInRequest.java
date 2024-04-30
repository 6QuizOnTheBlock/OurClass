package com.quiz.ourclass.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class MemberSignInRequest {

    private String email;


    private MemberSignInRequest (String email) {
        this.email = email;
    }


    public static MemberSignInRequest of (String email) {
        return builder().email(email).build();
    }
}
