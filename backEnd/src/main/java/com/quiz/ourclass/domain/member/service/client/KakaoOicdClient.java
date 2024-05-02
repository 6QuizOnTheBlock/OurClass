package com.quiz.ourclass.domain.member.service.client;


import com.quiz.ourclass.domain.member.dto.response.OIDCPublicKeysResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/*
* (name : 실제 구현체가 Application Context에 빈으로 등록될 때 이름
*  url  : 요청을 보낼 EndPoint)
* */
@FeignClient(name = "kakao-oicd-client", url = "https://kauth.kakao.com")
public interface KakaoOicdClient {

    // 스프링 어노테이션으로 요청 보내기 가능
    @GetMapping("/.well-known/jwks.json")
    OIDCPublicKeysResponse getKakaoOIDCOpenKeys();

}

