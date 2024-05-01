package com.quiz.ourclass.domain.member.controller.docs;

import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "MemberController", description = "회원관리 API")
public interface MemberControllerDocs {


    @Operation(summary = "회원가입(기본 정보)",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "회원가입에 성공하였습니다.",
                content = @Content(schema = @Schema(implementation = TokenDTO.class))
            )
        })
    @PostMapping
    ResponseEntity<ResultResponse<?>> signUp(MemberSignUpRequest request);


    @Operation(summary = "로그인",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "로그인에 성공하였습니다.",
                content = @Content(schema = @Schema(implementation = TokenDTO.class))
            ),
            @ApiResponse(responseCode = "404",
                description = "God damn it! 기존에 존재하는 회원입니다.",
                content = @Content(schema = @Schema(implementation = TokenDTO.class))
            )
        })
    @PostMapping
    ResponseEntity<ResultResponse<?>>  signIn (MemberSignInRequest request);
}
