package com.quiz.ourclass.domain.chat.controller;

import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;
import com.quiz.ourclass.domain.chat.service.ChatFilterService;
import com.quiz.ourclass.global.dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResultResponse<?>> register(
        @PathVariable(value = "organizationId") Long organizationId,
        @RequestBody ChatFilterRequest request
    ) {
        Long badWordId = chatFilterService.register(organizationId, request);
        return ResponseEntity.ok(ResultResponse.success(badWordId));
    }
}
