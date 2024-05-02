package com.quiz.ourclass.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class OIDCDecodePayload {


    // 발행자 -> 카카오는 https://kauth.kakao.com 로 고정
    private String iss;

    // Client Id
    private String aud;

    // oauth provider account unique id
    private String sub;

    // 토큰 발급한 사람의 Email
    private String email;

    // 토큰 발급한 사람의 이름
    private String nickname;


    public OIDCDecodePayload(String iss, String aud, String sub, String email, String nickname) {
        this.iss = iss;
        this.aud = aud;
        this.sub = sub;
        this.email = email;
        this.nickname = nickname;
    }

    public static OIDCDecodePayload of (String iss, String aud, String sub, String email, String nickname){
        return builder().iss(iss).aud(aud).sub(sub).email(email).nickname(nickname).build();
    }
}
