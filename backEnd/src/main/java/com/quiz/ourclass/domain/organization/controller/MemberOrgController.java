package com.quiz.ourclass.domain.organization.controller;

import com.quiz.ourclass.domain.organization.dto.request.RelationRequest;
import com.quiz.ourclass.domain.organization.dto.request.TagGreetingRequest;
import com.quiz.ourclass.domain.organization.dto.request.UpdateExpRequest;
import com.quiz.ourclass.domain.organization.dto.response.MemberDetailResponse;
import com.quiz.ourclass.domain.organization.dto.response.OrganizationHomeResponse;
import com.quiz.ourclass.domain.organization.dto.response.RelationResponse;
import com.quiz.ourclass.domain.organization.dto.response.RelationSimpleResponse;
import com.quiz.ourclass.domain.organization.dto.response.UpdateExpResponse;
import com.quiz.ourclass.domain.organization.service.MemberOrgService;
import com.quiz.ourclass.global.dto.ResultResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
@Slf4j
public class MemberOrgController implements MemberOrgControllerDocs {

    private final MemberOrgService memberOrgService;

    @PatchMapping("/{id}/point")
    public ResponseEntity<ResultResponse<UpdateExpResponse>> updateMemberExp(@PathVariable long id,
        @RequestBody UpdateExpRequest updateExpRequest) {
        UpdateExpResponse updateExpResponse = memberOrgService.updateMemberExp(
            id, updateExpRequest);
        return ResponseEntity.ok(ResultResponse.success(updateExpResponse));
    }

    @GetMapping("/{id}/relation")
    public ResponseEntity<ResultResponse<RelationResponse>> getMemberRelation(
        @PathVariable long id, RelationRequest relationRequest) {
        RelationResponse relationResponse = memberOrgService.getMemberRelation(
            id, relationRequest);
        return ResponseEntity.ok(ResultResponse.success(relationResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse<MemberDetailResponse>> getMemberDetail(
        @PathVariable long id, long memberId) {
        MemberDetailResponse memberDetail = memberOrgService.getMemberDetail(id, memberId);
        return ResponseEntity.ok(ResultResponse.success(memberDetail));
    }

    @GetMapping("/{id}/relations")
    public ResponseEntity<ResultResponse<List<RelationSimpleResponse>>> getMemberRelations(
        @PathVariable long id, long memberId, @RequestParam(required = false) Long limit) {
        List<RelationSimpleResponse> relations = memberOrgService.getMemberRelations(
            id, memberId, limit);
        return ResponseEntity.ok(ResultResponse.success(relations));
    }

    @GetMapping("/{id}/home")
    public ResponseEntity<ResultResponse<OrganizationHomeResponse>> getOrganizationHome(
        @PathVariable long id) {
        OrganizationHomeResponse homeResponse = memberOrgService.getOrganizationHome(id);
        return ResponseEntity.ok(ResultResponse.success(homeResponse));
    }

    @PostMapping("/tag")
    public ResponseEntity<ResultResponse<Integer>> tagGreet(
        @RequestBody TagGreetingRequest tagGreetingRequest) {
        int tagGreetingCount = memberOrgService.tagGreeting(tagGreetingRequest);
        return ResponseEntity.ok(ResultResponse.success(tagGreetingCount));
    }
}
