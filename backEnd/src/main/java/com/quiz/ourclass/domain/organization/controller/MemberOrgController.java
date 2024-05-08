package com.quiz.ourclass.domain.organization.controller;

import com.quiz.ourclass.domain.organization.dto.request.UpdateExpRequest;
import com.quiz.ourclass.domain.organization.dto.response.UpdateExpResponse;
import com.quiz.ourclass.domain.organization.service.MemberOrgService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
@Slf4j
public class MemberOrgController implements MemberOrgControllerDocs {

    private final MemberOrgService memberOrgService;

    @PatchMapping("/{id}/point")
    public ResponseEntity<ResultResponse<?>> updateMemberExp(@PathVariable long id,
        @RequestBody UpdateExpRequest updateExpRequest) {
        UpdateExpResponse updateExpResponse = memberOrgService.updateMemberExp(
            id, updateExpRequest);
        return ResponseEntity.ok(ResultResponse.success(updateExpResponse));
    }
}
