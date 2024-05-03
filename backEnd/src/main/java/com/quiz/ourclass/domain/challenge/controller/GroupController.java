package com.quiz.ourclass.domain.challenge.controller;

import com.quiz.ourclass.domain.challenge.service.GroupService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/group/matchingroom")
    public ResponseEntity<ResultResponse<?>> createMatchingRoom(long challengeId) {
        return ResponseEntity.ok(
            ResultResponse.success(groupService.createMatchingRoom(challengeId)));
    }
}
