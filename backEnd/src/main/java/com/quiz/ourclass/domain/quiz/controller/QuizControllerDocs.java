package com.quiz.ourclass.domain.quiz.controller;

import com.quiz.ourclass.domain.quiz.dto.QuizGameDTO;
import com.quiz.ourclass.domain.quiz.dto.request.MakingQuizRequest;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "QuizControllerDocs", description = "Quiz API")
public interface QuizControllerDocs {

    @Operation(summary = "퀴즈 생성",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")", content = @Content),
            @ApiResponse(responseCode = "404", description = """
                (message : "학급을 찾을 수 없습니다.")
                            
                (message : "담당자와 단체가 일치하지 않습니다.")
                """, content = @Content)
        })
    @PutMapping("")
    public ResponseEntity<ResultResponse<?>> makingQuiz(@RequestBody MakingQuizRequest request);


    @Operation(summary = "퀴즈 리스트 불러오기",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = QuizGameDTO.class))),
            @ApiResponse(responseCode = "404", description = "(message : \"해당 단체에 퀴즈가 존재하지 않습니다.\")",
                content = @Content)
        })
    @GetMapping("/{orgId}")
    public ResponseEntity<ResultResponse<?>> getQuizList(@PathVariable("orgId") long orgId);
}
