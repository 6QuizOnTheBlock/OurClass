package com.quiz.ourclass.domain.member.service.oidc;

import com.quiz.ourclass.domain.member.dto.OIDCDecodePayload;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class OidcUtilImpl implements OidcUtil {

    private final String KID = "kid";




    // 0. 토큰 안에서 kid (유효성 검증할 수 있는 키의 id)를 특정해서 가져온다.
    public String getKidFromUnsignedTokenHeader(String token, String iss, String aud) {
        return (String) getUnsignedTokenClaims(token, iss, aud).getHeader().get(KID);
    }


    // 1. ID 토큰의 영역을 구분자인 온점(.) 기준으로 헤더, 페이로드, 서명을 분리 한다.
    private String getUnsignedToken(String token) {
        String [] splitToken = token.split("\\.");
        if(splitToken.length != 3) throw new GlobalException(ErrorCode.OIDC_INVALID_TOKEN);
        return splitToken[0] + "." + splitToken[1] + ".";
    }


    /*
    * 2. Claims 열기 -> Header 와 Payload 얻기 (우리가 원하는 발행자, 발행 요청한 서비스(울반)이 아니면 유효하지 않은 토큰 에러를 낸다.)
    * */
    public Jwt<Header, Claims> getUnsignedTokenClaims (String token, String iss, String aud) {
        try {
            return Jwts.parserBuilder()
                .requireAudience(aud)
                .requireIssuer(iss)
                .build()
                .parseClaimsJwt(getUnsignedToken(token));
        } catch (ExpiredJwtException e){
            throw new GlobalException(ErrorCode.EXPIRED_TOKEN);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new GlobalException(ErrorCode.INVALID_TOKEN);
        }
    }

    /* 3. payLoad 분해하기 */
    public OIDCDecodePayload getOIDCTokenBody(String token, String modulus, String exponent) {
        Claims body = getOIDCTokenJws(token, modulus, exponent).getBody();

        return new OIDCDecodePayload(
            body.getIssuer(),
            body.getAudience(),
            body.getSubject(),
            body.get("email",String.class),
            body.get("nickname", String.class));
    }


    /*  4. 만든 RSA 키로 토큰 인증
        JWS = JSON Web Signature  */
    public Jws<Claims> getOIDCTokenJws(String token, String modulus, String exponent) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(getRSAPublicKey(modulus,exponent))
                .build()
                .parseClaimsJws(token);
        } catch (ExpiredJwtException e){
            throw new GlobalException(ErrorCode.EXPIRED_TOKEN);
        } catch (Exception e){
            log.error(e.toString());
            throw new GlobalException(ErrorCode.INVALID_TOKEN);
        }
    }




    /* 5. id-token에 적힌 공개키 재료인 modulus와 exponent를 통해, 공개키를 생성한다.
    *
    * */
    private Key getRSAPublicKey(String modulus, String exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte [] decodeN = Base64.getUrlDecoder().decode(modulus);
        byte [] decodeE = Base64.getUrlDecoder().decode(exponent);

        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        return keyFactory.generatePublic(keySpec);

    }
}
