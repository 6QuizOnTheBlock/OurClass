package com.quiz.ourclass.domain.member.controller;

import com.quiz.ourclass.domain.member.controller.docs.MemberControllerDocs;
import com.quiz.ourclass.domain.member.dto.request.DefaultImageRequest;
import com.quiz.ourclass.domain.member.dto.request.DeveloperAtRtRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberUpdateRequest;
import com.quiz.ourclass.domain.member.dto.request.UpdateFcmTokenRequest;
import com.quiz.ourclass.domain.member.service.MemberService;
import com.quiz.ourclass.domain.member.service.client.KakaoOicdClient;
import com.quiz.ourclass.global.config.annotation.LogExclusion;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;
    private final KakaoOicdClient kakaoOicdClient;

    /*  1. 회원가입   */
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResultResponse<?>> signUp(@ModelAttribute MemberSignUpRequest request) {
        return ResponseEntity.ok(ResultResponse.success(memberService.signUpProcess(request)));
    }

    /*  2. 로그인    */
    @PostMapping("/sign-in")

    public ResponseEntity<ResultResponse<?>> signIn(@RequestBody MemberSignInRequest request) {

        return ResponseEntity.ok(ResultResponse.success(memberService.signInProcess(request)));
    }

    /* 4. 테스트용  */
    @GetMapping("/kakao-keys")
    public ResponseEntity<ResultResponse<?>> getKakaoKeys() {

        return ResponseEntity.ok(ResultResponse.success(kakaoOicdClient.getKakaoOIDCOpenKeys()));
    }

    /* 5. id-Token 받아서 Decoding 하기 */
    @PostMapping("/decode-id-token")
    public ResponseEntity<ResultResponse<?>> decodeIdToken(@RequestParam String idToken) {
        log.info(idToken);
//        return ResponseEntity.ok(ResultResponse.success(oicdUtil.getUnsignedTokenClaims(idToken,"https://kauth.kakao.com", "edbf10bd8627e6eb676872109e996a9e")));
        return null;
    }


    /* 6 개발자용 Access, RefreshToken 발급 */
    @PostMapping("/developer-At")
    public ResponseEntity<ResultResponse<?>> getAtRt(@RequestBody DeveloperAtRtRequest request) {

        return ResponseEntity.ok(
            ResultResponse.success(memberService.giveDeveloperAccessToken(request)));
    }

    /* 7. 클라이언트로부터 받은 FCM 저장 */
    @PostMapping("/fcm")
    public ResponseEntity<ResultResponse<Void>> saveFcmToken(
        @RequestBody UpdateFcmTokenRequest request) {
        memberService.saveFcmToken(request);
        return ResponseEntity.ok(ResultResponse.success(null));
    }

    /* 8. 기본 이미지 업데이트 */

    @PatchMapping("/default-image")
    public ResponseEntity<ResultResponse<?>> updateDefaultImage(
        @ModelAttribute DefaultImageRequest request) {
        return ResponseEntity.ok(
            ResultResponse.success(memberService.updateDefaultImage(request).getPhoto()));
    }

    /* 8. 기본 이미지 조회 */
    @LogExclusion
    @GetMapping("/default-image")
    public ResponseEntity<ResultResponse<?>> getDefaultImages() {
        return ResponseEntity.ok(ResultResponse.success(memberService.getDefaultImages()));
    }

    /* 9. 현 유저의 회원 정보 주기 */
    @GetMapping("/")
    public ResponseEntity<ResultResponse<?>> rememberMe() {

        return ResponseEntity.ok(
            ResultResponse.success(memberService.rememberMe()));
    }

    /* 10. 멤버 프로필 이미지 수정 */
    @PatchMapping("/photo")
    public ResponseEntity<ResultResponse<?>> updateProfile(
        @ModelAttribute MemberUpdateRequest request) {
        return ResponseEntity.ok(ResultResponse.success(memberService.updateProfile(request)));
    }

    @DeleteMapping("/")
    public ResponseEntity<ResultResponse<?>> deleteMe() {
        memberService.deleteMe();
        return ResponseEntity.ok(ResultResponse.success(null));
    }
}
