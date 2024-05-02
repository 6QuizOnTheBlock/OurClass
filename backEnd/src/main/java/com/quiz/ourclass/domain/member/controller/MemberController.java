package com.quiz.ourclass.domain.member.controller;

import com.quiz.ourclass.domain.member.controller.docs.MemberControllerDocs;
import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.service.MemberService;
import com.quiz.ourclass.domain.member.service.client.KakaoOicdClient;
import com.quiz.ourclass.domain.member.service.oidc.OidcUtilImpl;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;
    private final KakaoOicdClient kakaoOicdClient;

    /*  1. 회원가입   */
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResultResponse<?>> signUp(@ModelAttribute MemberSignUpRequest request) {
        return ResponseEntity.ok(ResultResponse.success(memberService.signUpProcess(request)));
    }

    /*  2. 로그인    */
    @PostMapping("/sign-in")

    public ResponseEntity<ResultResponse<?>>  signIn (@RequestBody MemberSignInRequest request) {

        return ResponseEntity.ok(ResultResponse.success(memberService.signInProcess(request)));
    }

    /* 4. 테스트용  */
    @GetMapping("/kakao-keys")
    public ResponseEntity<ResultResponse<?>> getKakaoKeys(){

        return ResponseEntity.ok(ResultResponse.success(kakaoOicdClient.getKakaoOIDCOpenKeys()));
    }

    /* 5. id-Token 받아서 Decoding 하기 */
    @PostMapping("/decode-id-token")
    public ResponseEntity<ResultResponse<?>> decodeIdToken (@RequestParam String idToken) {
        System.out.println(idToken);
//        return ResponseEntity.ok(ResultResponse.success(oicdUtil.getUnsignedTokenClaims(idToken,"https://kauth.kakao.com", "edbf10bd8627e6eb676872109e996a9e")));
        return null;
    }
}
