package com.quiz.ourclass.domain.challenge.controller;

import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "GroupController", description = "함께달리기 그룹매칭 API")
public interface GroupControllerDocs {

    @Operation(summary = "함께달리기 그룹 매칭 대기방 생성\n",
        description = "그룹 리더가 블루투스 매칭을 시작, 임시 대기방이 생성됩니다."
            + "임시 대기방의 ID (Redis KEY) 응답.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = String.class, description = "대기방 KEY")))
        })
    @PostMapping("/groups/matchingroom")
    ResponseEntity<ResultResponse<?>> createMatchingRoom(
        @Parameter(description = "함께달리기 ID", required = true, in = ParameterIn.QUERY)
        long challengeId
    );

    @Operation(summary = "함께달리기 그룹 초대 응답",
        description = """
            그룹 블루투스 매칭 중 초대에 수락 or 거부 응답을 보냅니다.
                        
            수락 시 해당 대기방 참여.
                        
            그룹 리더에게 SSE(INVITE_RESPONSE) 전송.""",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Boolean.class, description = "초대 응답")))
        })
    @PostMapping("/groups/join")
    ResponseEntity<ResultResponse<?>> joinMatchingRoom(
        @Parameter(description = "그룹 대기방 KEY", required = true, in = ParameterIn.QUERY)
        String key,
        @Parameter(description = "초대 수락 여부", required = true, in = ParameterIn.QUERY)
        boolean joinStatus
    );

    @Operation(summary = "함께달리기 그룹 최종 생성",
        description = "그룹 리더가 최종 그룹원을 확정하고 그룹을 생성합니다.\n"
            + "전체 멤버에게 SSE(CREATE_GROUP) 전송",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class, description = "생성된 그룹 id")))
        })
    @PostMapping("/groups")
    ResponseEntity<ResultResponse<?>> createGroup(
        @Parameter(description = "그룹 대기방 KEY", required = true, in = ParameterIn.QUERY)
        String key
    );

    @Operation(summary = "함께달리기 그룹 대기 멤버 추방",
        description = "그룹 리더가 그룹 매칭 대기방에 있는 멤버를 추방합니다.\n"
            + "추방된 멤버에게 SSE(KICK_MEMBER) 전송",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")")
        })
    @DeleteMapping("/groups/matching")
    ResponseEntity<ResultResponse<?>> deleteMatchingMember(
        @Parameter(description = "그룹 대기방 KEY", required = true, in = ParameterIn.QUERY)
        String key,
        @Parameter(description = "추방 멤버 ID", required = true, in = ParameterIn.QUERY)
        Long memberId
    );

    @Operation(summary = "함께달리기 그룹 멤버 초대",
        description = "그룹 리더가 블루투스로 연결된 사용자에게 그룹 초대 요청을 전달합니다.\n"
            + "초대 멤버에게 SSE(INVITE_REQUEST) 전송",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")")
        })
    @GetMapping("/groups/invite")
    ResponseEntity<ResultResponse<?>> inviteMatchingRoom(
        @Parameter(description = "그룹 대기방 KEY", required = true, in = ParameterIn.QUERY)
        String key,
        @Parameter(description = "초대 멤버 ID", required = true, in = ParameterIn.QUERY)
        Long memberId
    );
}
