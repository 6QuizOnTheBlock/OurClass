package com.quiz.ourclass.domain.member.service.oidc;

import com.quiz.ourclass.domain.member.dto.OIDCDecodePayload;

public interface OidcUtil {


    public String getKidFromUnsignedTokenHeader(String token, String iss, String aud);

    public OIDCDecodePayload getOIDCTokenBody(String token, String modulus, String exponent);



}
