package com.quiz.ourclass.domain.organization.controller;

import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    ResponseEntity<ResultResponse<?>> getOrganizations();
}
