package com.quiz.ourclass.domain.member.controller.docs;

import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.dto.request.UpdateFcmTokenRequest;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "MemberController", description = "회원관리 API")
public interface MemberControllerDocs {


    @Operation(summary = "회원가입(기본 정보)",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "회원가입에 성공하였습니다."
            ),
            @ApiResponse(responseCode = "404",
                description = "기존에 존재하는 회원입니다."),
            @ApiResponse(responseCode = "503",
                description = "OIDC 토큰 인증에 실패했습니다.")
        })
    @PostMapping
    ResponseEntity<ResultResponse<?>> signUp(MemberSignUpRequest request);


    @Operation(summary = "로그인",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "로그인에 성공하였습니다."
            ),
            @ApiResponse(responseCode = "404",
                description = "God damn it! 신규 회원입니다."
            ),
            @ApiResponse(responseCode = "503",
                description = "OIDC 토큰 인증에 실패했습니다.")
        })
    @PostMapping
    ResponseEntity<ResultResponse<?>> signIn(MemberSignInRequest request);

    @Operation(summary = "FCM 토큰 저장 및 갱신",
        description = "입력으로 들어오는 FCM 토큰을 저장 및 갱신 합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")")
        }
    )
    @PostMapping("/fcm")
    ResponseEntity<ResultResponse<Void>> saveFcmToken(
        @Parameter(name = "request", description = "FCM 토큰", required = true, in = ParameterIn.DEFAULT)
        @RequestBody UpdateFcmTokenRequest request);
}
