package com.quiz.ourclass.domain.member.controller;

import com.quiz.ourclass.domain.member.dto.request.MemberAdditionalInfoRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSigninRequest;
import com.quiz.ourclass.domain.member.service.MemberService;
import com.quiz.ourclass.global.dto.ApiResponse;
import com.quiz.ourclass.global.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*  1. 회원가입   */
    @PostMapping("/")
    public ApiResponse<?> signUp (MemberSignUpRequest request) throws Exception {
        return ApiResponse.success(memberService.signUpProcess(request));
    }

    /*  2. 추가정보 받기   */
    @PatchMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<?> setUpInfo (@AuthenticationPrincipal UserDetailsImpl userDetails,MemberAdditionalInfoRequest request){

        memberService.addingInfoProcess(userDetails, request);

        return ApiResponse.success("추가정보 기입에 성공했습니다.");
    }

    /*  3. 로그인    */
    @PostMapping("/sign-in")
    public ApiResponse<?> signIn (MemberSigninRequest request) {

        return ApiResponse.success(memberService.signInProcess(request));
    }

}
