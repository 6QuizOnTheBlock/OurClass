package com.quiz.ourclass.domain.member.controller;

import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.service.MemberService;
import com.quiz.ourclass.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/")
    public ApiResponse<?> signUp (MemberSignUpRequest request) throws Exception {
        return ApiResponse.success(memberService.signUpProcess(request));
    }

}
