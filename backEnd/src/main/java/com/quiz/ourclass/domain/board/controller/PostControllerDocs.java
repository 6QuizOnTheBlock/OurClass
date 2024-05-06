package com.quiz.ourclass.domain.board.controller;


import com.quiz.ourclass.domain.board.dto.request.PostRequest;
import com.quiz.ourclass.domain.board.dto.request.PostSliceRequest;
import com.quiz.ourclass.domain.board.dto.request.UpdatePostRequest;
import com.quiz.ourclass.domain.board.dto.response.PostDetailResponse;
import com.quiz.ourclass.domain.board.dto.response.PostListResponse;
import com.quiz.ourclass.global.dto.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "PostController", description = "SNS 게시글 API")
public interface PostControllerDocs {

    @Operation(
        summary = "게시글 작성",
        description = "입력으로 들어온 dto, multipart 로 게시글을 작성합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"멤버가 해당 단체 소속이 아닙니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"멤버가 존재하지 않습니다.\")", content = @Content),
            @ApiResponse(responseCode = "500", description = "(message : \"첨부한 파일이 S3에 업로드 되지 않았습니다.\")", content = @Content)
        })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResultResponse<?>> write(
        @Parameter(name = "organizationId", description = "단체 PK 값", required = true, in = ParameterIn.QUERY)
        @RequestParam("organizationId") Long classId,
        @Parameter(name = "request", description = "게시글 작성 DTO", required = true, in = ParameterIn.DEFAULT)
        @RequestPart(value = "request") PostRequest request,
        @Parameter(name = "file", description = "사진 파일", required = true, in = ParameterIn.DEFAULT)
        @RequestPart(value = "file", required = false) MultipartFile file
    );

    @Operation(
        summary = "게시글 수정",
        description = "path 입력으로 들어온 게시글 PK 값 기준으로 게시글을 수정합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"멤버가 해당 단체 소속이 아닙니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"멤버가 존재하지 않습니다.\")", content = @Content),
            @ApiResponse(responseCode = "500", description = "(message : \"첨부한 파일이 S3에 업로드 되지 않았습니다.\")", content = @Content)
        }
    )
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResultResponse<?>> modify(
        @Parameter(name = "id", description = "게시글 PK 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "id") Long id,
        @Parameter(name = "request", description = "게시글 작성 DTO", required = true, in = ParameterIn.DEFAULT)
        @RequestPart(value = "request") UpdatePostRequest request,
        @Parameter(name = "file", description = "사진 파일", required = true, in = ParameterIn.DEFAULT)
        @RequestPart(value = "file", required = false) MultipartFile file
    );

    @Operation(
        summary = "게시글 삭제",
        description = "path 입력으로 들어온 게시글 PK 값 기준으로 게시글을 삭제합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"멤버가 작성한 게시글을 찾을 수 없습니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = "(message : \"게시글을 찾을 수 없습니다.\")", content = @Content)
        }
    )
    @DeleteMapping(value = "{id}")
    ResponseEntity<ResultResponse<?>> delete(
        @Parameter(name = "id", description = "게시글 PK 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "id") Long id
    );

    @Operation(
        summary = "게시글 상세 조회",
        description = "path 입력으로 들어온 게시글 PK 값 기준으로 게시글 상세조회 합니다. (댓글 포함)",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = PostDetailResponse.class))),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                                
                (message : "게시글을 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @GetMapping(value = "/{id}")
    ResponseEntity<ResultResponse<?>> detailView(
        @Parameter(name = "id", description = "게시글 PK 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "id") Long id
    );

    @Operation(
        summary = "게시글 신고",
        description = "path 입력으로 들어온 게시글 PK 값 기준으로 게시글을 신고하여 해당 단체 관리자에게 FCM 알림을 전송합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"멤버가 해당 단체 소속이 아닙니다.\")", content = @Content),
            @ApiResponse(responseCode = "404", description = """
                (message : "멤버가 존재하지 않습니다.")
                                
                (message : "게시글을 찾을 수 없습니다.")
                """, content = @Content),
        }
    )
    @PostMapping(value = "/{id}/report")
    ResponseEntity<ResultResponse<?>> report(
        @Parameter(name = "id", description = "게시글 PK 값", required = true, in = ParameterIn.PATH)
        @PathVariable(value = "id") Long id
    );

    @Operation(
        summary = "게시글 목록 조회",
        description = "쿼리 입력으로 들어온 값 기준으로 게시글 목록을 조회합니다. (page 0부터 시작입니다!)",
        responses = {
            @ApiResponse(responseCode = "200", description = "(message : \"Success\")",
                content = @Content(schema = @Schema(implementation = PostListResponse.class))),
            @ApiResponse(responseCode = "403", description = "(message : \"잘못된 입력 입니다.\")", content = @Content),
        }
    )
    @GetMapping
    ResponseEntity<ResultResponse<?>> listView(
        PostSliceRequest request
    );
}
