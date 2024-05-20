package com.quiz.ourclass.domain.organization.controller;

import com.quiz.ourclass.domain.organization.dto.InviteCodeDTO;
import com.quiz.ourclass.domain.organization.dto.OrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.OrganizationResponse;
import com.quiz.ourclass.domain.organization.dto.request.UpdateOrganizationRequest;
import com.quiz.ourclass.domain.organization.dto.response.MemberRankPoint;
import com.quiz.ourclass.domain.organization.dto.response.OrganizationSummaryResponse;
import com.quiz.ourclass.domain.organization.dto.response.UpdateOrganizationResponse;
import com.quiz.ourclass.domain.organization.service.OrganizationService;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import com.quiz.ourclass.global.dto.ResultResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController implements OrganizationControllerDocs {

    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<ResultResponse<?>> createOrganization(
        @RequestBody OrganizationRequest organizationRequest) {
        Long organizationId = organizationService.createOrganization(organizationRequest);
        return ResponseEntity.ok(ResultResponse.success(organizationId));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<?>> getOrganizations() {
        List<OrganizationResponse> organizations = organizationService.getOrganizations();
        return ResponseEntity.ok(ResultResponse.success(organizations));
    }

    @GetMapping("/{id}/code")
    public ResponseEntity<ResultResponse<?>> getOrganizationCode(@PathVariable long id) {
        InviteCodeDTO code = organizationService.getOrganizationCode(id);
        return ResponseEntity.ok(ResultResponse.success(code));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<ResultResponse<?>> joinOrganization(@PathVariable long id,
        @RequestBody InviteCodeDTO inviteCodeDTO) {
        Long organizationId = organizationService.joinOrganization(id, inviteCodeDTO);
        return ResponseEntity.ok(ResultResponse.success(organizationId));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<ResultResponse<?>> getOrganizationMembers(@PathVariable long id) {
        List<MemberSimpleDTO> members = organizationService.getOrganizationMembers(id);
        return ResponseEntity.ok(ResultResponse.success(members));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponse<?>> updateOrganizationName(@PathVariable long id,
        @RequestBody UpdateOrganizationRequest updateOrganizationRequest) {
        UpdateOrganizationResponse updateOrganizationResponse = organizationService.updateOrganizationName(
            id, updateOrganizationRequest);
        return ResponseEntity.ok(ResultResponse.success(updateOrganizationResponse));
    }

    @GetMapping("/{id}/rank")
    public ResponseEntity<ResultResponse<?>> getRanking(@PathVariable long id) {
        List<MemberRankPoint> memberRankPoints = organizationService.getRanking(id);
        return ResponseEntity.ok(ResultResponse.success(memberRankPoints));
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<ResultResponse<?>> getSummary(@PathVariable long id) {
        OrganizationSummaryResponse summaryResponse = organizationService.getSummary(id);
        return ResponseEntity.ok(ResultResponse.success(summaryResponse));
    }
}

