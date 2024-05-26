package com.quiz.ourclass.domain.chat.controller;

import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;
import com.quiz.ourclass.domain.chat.dto.request.ChatFilterSliceRequest;
import com.quiz.ourclass.domain.chat.dto.response.ChatFilterResponse;
import com.quiz.ourclass.domain.chat.service.ChatFilterService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filters")
@RequiredArgsConstructor
public class ChatFilterController implements ChatFilterControllerDocs {

    private final ChatFilterService chatFilterService;

    @PostMapping("/{organizationId}")
    public ResponseEntity<ResultResponse<Long>> register(
        @PathVariable(value = "organizationId") Long organizationId,
        @RequestBody ChatFilterRequest request
    ) {
        Long badWordId = chatFilterService.register(organizationId, request);
        return ResponseEntity.ok(ResultResponse.success(badWordId));
    }

    @PatchMapping("/{organizationId}/{id}")
    public ResponseEntity<ResultResponse<Boolean>> modify(
        @PathVariable(value = "organizationId") Long organizationId,
        @PathVariable(value = "id") Long id,
        @RequestBody ChatFilterRequest request
    ) {
        Boolean isModify = chatFilterService.modify(organizationId, id, request);
        return ResponseEntity.ok(ResultResponse.success(isModify));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse<Boolean>> delete(
        @PathVariable(value = "id") Long id
    ) {
        Boolean isDelete = chatFilterService.delete(id);
        return ResponseEntity.ok(ResultResponse.success(isDelete));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<ChatFilterResponse>> select(
        ChatFilterSliceRequest request
    ) {
        ChatFilterResponse response = chatFilterService.select(request);
        return ResponseEntity.ok(ResultResponse.success(response));
    }
}
