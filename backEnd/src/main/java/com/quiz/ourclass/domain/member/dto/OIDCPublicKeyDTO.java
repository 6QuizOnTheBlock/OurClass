package com.quiz.ourclass.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OIDCPublicKeyDTO {
    private String kid;     // 공개키 ID
    private String alg;     // 공개 키 암호화에 쓰여진 알고리즘
    private String use;     // 공개키의 용도 sig(서명)으로 고정
    private String n;       // 공개키 모듈 (공개키는 n과 e 쌍으로 구성됨)
    private String e;       // 공개키 지수 (공개키는 n과 e 쌍으로 구성됨)
}
