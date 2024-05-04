package com.quiz.ourclass.domain.organization.controller;

import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.service.OrganizationService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<ResultResponse<?>> createOrganization(
        @RequestBody OrganizationRequest organizationRequest) {
        Long organizationId = organizationService.createOrganization(organizationRequest);
        return ResponseEntity.ok(ResultResponse.success(organizationId));
    }
}
