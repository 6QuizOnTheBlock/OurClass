package com.quiz.ourclass.domain.organization.controller;

import com.quiz.ourclass.domain.organization.dto.InviteCodeDTO;
import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import com.quiz.ourclass.domain.organization.dto.request.UpdateOrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.response.MemberRankPoint;
import com.quiz.ourclass.domain.organization.dto.response.OrganizationSummaryResponse;
import com.quiz.ourclass.domain.organization.dto.response.UpdateOrganizationResponse;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    ResponseEntity<ResultResponse<Long>> createOrganization(
        @RequestBody
        OrganizationRequest organizationRequest
    );

    @Operation(summary = "학급 목록 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = OrganizationResponse.class)))
        })
    @GetMapping
    ResponseEntity<ResultResponse<List<OrganizationResponse>>> getOrganizations(
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
    ResponseEntity<ResultResponse<InviteCodeDTO>> getOrganizationCode(
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
    ResponseEntity<ResultResponse<Long>> joinOrganization(
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
    ResponseEntity<ResultResponse<List<MemberSimpleDTO>>> getOrganizationMembers(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id
    );

    @Operation(summary = "학급 정보 수정",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = UpdateOrganizationResponse.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"해당 학급의 관리자가 아닙니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"멤버가 존재하지 않습니다.\")", content = @Content)
        })
    @PatchMapping("/{id}")
    ResponseEntity<ResultResponse<UpdateOrganizationResponse>> updateOrganizationName(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id,
        @RequestBody
        UpdateOrganizationRequest updateOrganizationRequest
    );

    @Operation(summary = "학급 포인트 랭킹 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = MemberRankPoint.class)))
        })
    @GetMapping("/{id}/rank")
    ResponseEntity<ResultResponse<List<MemberRankPoint>>> getRanking(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id
    );

    @Operation(summary = "학급 통계 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = OrganizationSummaryResponse.class)))
        })
    @GetMapping("/{id}/summary")
    ResponseEntity<ResultResponse<OrganizationSummaryResponse>> getSummary(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id
    );
}
