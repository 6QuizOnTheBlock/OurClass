package com.quiz.ourclass.domain.member.controller.docs;

import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.dto.request.MemberAdditionalInfoRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.global.dto.ApiResponse;
import com.quiz.ourclass.global.util.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "MemberController", description = "회원관리 API")
public interface MemberControllerDocs {


    @Operation(summary = "회원가입(기본 정보)",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                description = "회원가입(기본 정보) 입력에 성공하였습니다.",
                content = @Content(schema = @Schema(implementation = TokenDTO.class))
            )
        })
    @PostMapping
    public ResponseEntity<ResultResponse<?>> signUp(MemberSignUpRequest request);


    @Operation(summary = "회원가입(추가 정보)",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                description = "회원가입(추가 정보) 입력에 성공하였습니다. ")
        })
    @PatchMapping
    public ResponseEntity<ResultResponse<?>> setUpInfo(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        MemberAdditionalInfoRequest request);


    @Operation(summary = "로그인",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                description = "로그인에 성공하였습니다.",
                content = @Content(schema = @Schema(implementation = TokenDTO.class))
            )
        })
    @PostMapping
    public ResponseEntity<ApiResponse<?>>  signIn (MemberSignInRequest request);
}
