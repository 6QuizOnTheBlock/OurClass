package com.quiz.ourclass.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignInRequest {

    private String idToken;

}
