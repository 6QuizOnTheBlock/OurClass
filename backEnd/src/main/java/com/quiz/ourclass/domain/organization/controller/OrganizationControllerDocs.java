package com.quiz.ourclass.domain.organization.controller;

import com.quiz.ourclass.domain.organization.dto.InviteCodeDTO;
import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "OrganizationController", description = "학급 API")
public interface OrganizationControllerDocs {

    @Operation(summary = "학급 생성",
        description = "선생용 API. 학급 생성.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class)))
        })
    @PostMapping
    ResponseEntity<ResultResponse<?>> createOrganization(
        @RequestBody
        OrganizationRequest organizationRequest
    );

    @Operation(summary = "학급 목록 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = OrganizationResponse.class)))
        })
    @GetMapping
    ResponseEntity<ResultResponse<?>> getOrganizations(
    );

    @Operation(summary = "학급 가입코드 생성",
        description = "선생용 API. 학급 가입 코드 생성. 생성된 코드는 10분간 유효합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = InviteCodeDTO.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"해당 학급의 관리자가 아닙니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"멤버가 존재하지 않습니다.\")", content = @Content)
        })
    @GetMapping("/{id}/code")
    ResponseEntity<ResultResponse<?>> getOrganizationCode(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id
    );

    @Operation(summary = "학급 가입",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = """
                (message : "학급 가입이 불가능한 상태입니다.")
                (message : "학급 가입코드가 일치하지 않습니다.")
                (message : "이미 가입한 학급입니다.")
                """, content = @Content),
            @ApiResponse(responseCode = "403", description = "(message : \"해당 학급의 관리자가 아닙니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                (message : "학급을 찾을 수 없습니다.")
                """, content = @Content)
        })
    @PostMapping("/{id}/join")
    ResponseEntity<ResultResponse<?>> joinOrganization(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id,
        @RequestBody
        InviteCodeDTO inviteCodeDTO
    );

    @Operation(summary = "학급 멤버 목록 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = MemberSimpleDTO.class))),
            @ApiResponse(responseCode = "404", description = "(message : \"학급을 찾을 수 없습니다.\")", content = @Content)
        })
    @GetMapping("/{id}/members")
    ResponseEntity<ResultResponse<?>> getOrganizationMembers(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id
    );
}
