package com.quiz.ourclass.domain.member.service;

import com.quiz.ourclass.domain.member.dto.OIDCDecodePayload;
import com.quiz.ourclass.domain.member.dto.OIDCPublicKeyDTO;
import com.quiz.ourclass.domain.member.dto.response.OIDCPublicKeysResponse;
import com.quiz.ourclass.domain.member.service.client.KakaoOicdClient;
import com.quiz.ourclass.domain.member.service.oidc.OidcUtilImpl;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OidcService {

    @Value("${oicd.iss}")
    private String iss;

    @Value("${oicd.aud}")
    private String aud;

    private final OidcUtilImpl oicdUtil;

    private final KakaoOicdClient kakaoOicdClient;

    /*
     * 1. 유효성 검증
     * (1) 토큰 디코딩 -> (2) kid 값 알아내기 -> (3) kid 이용, RSA key 재료 얻어내기 -> RSA를 통한 유효성 검증
     * */

    public OIDCDecodePayload certificatingIdToken(String idToken) {

        // 해당 토큰의 서명 인증 할 수 있는 공개 키의 id (kid) 특정
        String kid = oicdUtil.getKidFromUnsignedTokenHeader(idToken, iss, aud);
        // 카카오 인증 서버에서 이번 주기에 사용한 공개키 목록 전체 받아오기 -> 캐시화 필요!!! 너무 많이 요청하면 차단 당함!
        OIDCPublicKeysResponse keys = kakaoOicdClient.getKakaoOIDCOpenKeys();
        // 이번 주에 카카오가 제공하는 공개키 중에 내 Resource Owner 의 id-token 인증 가능한 key 받아오기
        OIDCPublicKeyDTO nowKey = keys.getKeys().stream().filter(key -> key.getKid().equals(kid))
            .findFirst().orElseThrow(null);

        if (nowKey == null) {
            throw new GlobalException(ErrorCode.CERTIFICATION_FAILED);
        }

        // 해당 키로 서명 인증, 예외 안 터지고 인증 되면 Body 가져옴
        return oicdUtil.getOIDCTokenBody(idToken, nowKey.getN(), nowKey.getE());


    }

}
