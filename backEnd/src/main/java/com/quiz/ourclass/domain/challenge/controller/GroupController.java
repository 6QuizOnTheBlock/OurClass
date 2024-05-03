package com.quiz.ourclass.domain.challenge.controller;

import com.quiz.ourclass.domain.challenge.service.GroupService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/groups/matchingroom")
    public ResponseEntity<ResultResponse<?>> createMatchingRoom(long challengeId) {
        return ResponseEntity.ok(
            ResultResponse.success(groupService.createMatchingRoom(challengeId)));
    }

    @PostMapping("/groups/join")
    public ResponseEntity<ResultResponse<?>> joinMatchingRoom(String key, boolean joinStatus) {
        return ResponseEntity.ok(
            ResultResponse.success(groupService.joinMatchingRoom(key, joinStatus)));
    }

    @PostMapping("/groups")
    public ResponseEntity<ResultResponse<?>> createGroup(String key) {
        return ResponseEntity.ok(
            ResultResponse.success(groupService.createGroup(key)));
    }

    @DeleteMapping("/groups/matching")
    public ResponseEntity<ResultResponse<?>> deleteMatchingMember(String key, Long id) {
        groupService.deleteMatchingMember(key, id);
        return ResponseEntity.ok(ResultResponse.success(null));
    }
}
