package com.quiz.ourclass.domain.organization.controller;

import com.quiz.ourclass.domain.organization.dto.request.RelationRequest;
import com.quiz.ourclass.domain.organization.dto.request.TagGreetingRequest;
import com.quiz.ourclass.domain.organization.dto.request.UpdateExpRequest;
import com.quiz.ourclass.domain.organization.dto.response.MemberDetailResponse;
import com.quiz.ourclass.domain.organization.dto.response.OrganizationHomeResponse;
import com.quiz.ourclass.domain.organization.dto.response.RelationResponse;
import com.quiz.ourclass.domain.organization.dto.response.RelationSimpleResponse;
import com.quiz.ourclass.domain.organization.dto.response.UpdateExpResponse;
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
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "MemberOrgControllerDocs", description = "학급 멤버 API")
public interface MemberOrgControllerDocs {

    @Operation(summary = "학급 멤버 포인트 수정",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = UpdateExpResponse.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"해당 학급의 관리자가 아닙니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"학급 멤버를 찾을 수 없습니다.\")", content = @Content)
        })
    @PatchMapping("/{id}/point")
    ResponseEntity<ResultResponse<UpdateExpResponse>> updateMemberExp(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id,
        @RequestBody
        UpdateExpRequest updateExpRequest
    );

    @Operation(summary = "학급 멤버 관계 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = RelationResponse.class))),
            @ApiResponse(responseCode = "404", description = "(message : \"두 멤버간의 관계를 찾을 수 없습니다.\")", content = @Content)
        })
    @GetMapping("/{id}/relation")
    ResponseEntity<ResultResponse<RelationResponse>> getMemberRelation(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id,
        RelationRequest relationRequest
    );

    @Operation(summary = "학급 멤버 상세 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = MemberDetailResponse.class))),
            @ApiResponse(responseCode = "404", description = "(message : \"학급 멤버를 찾을 수 없습니다.\")", content = @Content)
        })
    @GetMapping("/{id}")
    ResponseEntity<ResultResponse<MemberDetailResponse>> getMemberDetail(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id,
        @Parameter(description = "멤버 ID", required = true, in = ParameterIn.QUERY)
        long memberId
    );

    @Operation(summary = "멤버 친한친구 순서로 관계 조회",
        description = "limit 파라미터 없으면 전체조회, 있으면 상위 limit 갯수만큼 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = RelationSimpleResponse.class)))
        })
    @GetMapping("/{id}/relations")
    ResponseEntity<ResultResponse<List<RelationSimpleResponse>>> getMemberRelations(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id,
        @Parameter(description = "멤버 ID", required = true, in = ParameterIn.QUERY)
        long memberId,
        @RequestParam(required = false)
        @Parameter(description = "상위 n개만 조회 시 추가", in = ParameterIn.QUERY)
        Long limit
    );

    @Operation(summary = "학생 학급 홈 조회.",
        description = "친한 친구 기준 3명 함께 보여줌",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = OrganizationHomeResponse.class)))
        })
    @GetMapping("/{id}/home")
    ResponseEntity<ResultResponse<OrganizationHomeResponse>> getOrganizationHome(
        @PathVariable
        @Parameter(description = "학급 ID", required = true, in = ParameterIn.PATH)
        long id
    );

    @Operation(summary = "태깅 인사. 두 멤버 간의 태깅인사 횟수 반환",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "(message : \"두 멤버간의 관계를 찾을 수 없습니다.\")", content = @Content)
        })
    @PostMapping("/tag")
    ResponseEntity<ResultResponse<Integer>> tagGreet(
        @RequestBody
        TagGreetingRequest tagGreetingRequest
    );
}