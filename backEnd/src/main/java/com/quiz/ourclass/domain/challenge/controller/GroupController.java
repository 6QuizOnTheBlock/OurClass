package com.quiz.ourclass.domain.challenge.controller;

import com.quiz.ourclass.domain.challenge.dto.request.AutoGroupMatchingRequest;
import com.quiz.ourclass.domain.challenge.dto.response.AutoGroupMatchingResponse;
import com.quiz.ourclass.domain.challenge.dto.response.MatchingRoomResponse;
import com.quiz.ourclass.domain.challenge.service.GroupService;
import com.quiz.ourclass.global.dto.ResultResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class GroupController implements GroupControllerDocs {

    private final GroupService groupService;

    @PostMapping("/groups/matchingroom")
    public ResponseEntity<ResultResponse<MatchingRoomResponse>> createMatchingRoom(
        long challengeId) {
        return ResponseEntity.ok(
            ResultResponse.success(groupService.createMatchingRoom(challengeId)));
    }

    @PostMapping("/groups/join")
    public ResponseEntity<ResultResponse<Boolean>> joinMatchingRoom(String key,
        boolean joinStatus) {
        return ResponseEntity.ok(
            ResultResponse.success(groupService.joinMatchingRoom(key, joinStatus)));
    }

    @PostMapping("/groups")
    public ResponseEntity<ResultResponse<Long>> createGroup(String key) {
        return ResponseEntity.ok(
            ResultResponse.success(groupService.createGroup(key)));
    }

    @DeleteMapping("/groups/matching")
    public ResponseEntity<ResultResponse<Boolean>> deleteMatchingMember(String key, Long memberId) {
        groupService.deleteMatchingMember(key, memberId);
        return ResponseEntity.ok(ResultResponse.success(true));
    }

    @GetMapping("/groups/invite")
    public ResponseEntity<ResultResponse<Boolean>> inviteMatchingRoom(String key, Long memberId) {
        groupService.inviteMatchingRoom(key, memberId);
        return ResponseEntity.ok(ResultResponse.success(true));
    }

    @GetMapping("/groups/matching")
    public ResponseEntity<ResultResponse<List<AutoGroupMatchingResponse>>> getGroupMatching(
        AutoGroupMatchingRequest autoGroupMatchingRequest) {
        List<AutoGroupMatchingResponse> autoGroupMatchingResponse =
            groupService.getGroupMatching(autoGroupMatchingRequest);
        return ResponseEntity.ok(ResultResponse.success(autoGroupMatchingResponse));
    }
}
