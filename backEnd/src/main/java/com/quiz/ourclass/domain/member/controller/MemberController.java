package com.quiz.ourclass.domain.member.controller;

import com.quiz.ourclass.domain.member.controller.docs.MemberControllerDocs;
import com.quiz.ourclass.domain.member.dto.request.MemberAdditionalInfoRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.service.MemberService;
import com.quiz.ourclass.global.dto.ResultResponse;
import com.quiz.ourclass.global.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    /*  1. 회원가입   */
    @PostMapping("/")

    public ResponseEntity<ResultResponse<?>> signUp (@RequestBody MemberSignUpRequest request)  {

        System.out.println(request.getEmail() + " " + request.getName()+ " " + request.getSocialType());

        return ResponseEntity.ok(ResultResponse.success(memberService.signUpProcess(request)));
    }

    /*  2. 추가정보 받기   */
    @PatchMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResultResponse<?>>  setUpInfo (@AuthenticationPrincipal UserDetailsImpl userDetails, MemberAdditionalInfoRequest request){

        memberService.addingInfoProcess(userDetails, request);

        return ResponseEntity.ok(ResultResponse.success("추가정보 기입에 성공했습니다."));
    }

    /*  3. 로그인    */
    @PostMapping("/sign-in")

    public ResponseEntity<ResultResponse<?>>  signIn (@RequestBody MemberSignInRequest request) {

        return ResponseEntity.ok(ResultResponse.success(memberService.signInProcess(request)));
    }

}
