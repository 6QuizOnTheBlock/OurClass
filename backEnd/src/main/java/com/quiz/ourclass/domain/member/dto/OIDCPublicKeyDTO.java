package com.quiz.ourclass.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OIDCPublicKeyDTO {
    private String kid;
    private String alg;
    private String use;
    private String n;
    private String e;
}
