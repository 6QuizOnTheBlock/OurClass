package com.quiz.ourclass.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class MemberSignUpRequest {

    String email;
    String name;
    String socialType;


}
