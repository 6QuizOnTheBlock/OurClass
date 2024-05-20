package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.domain.challenge.entity.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "레포트 조회 응답 DTO")
public class ReportResponse {

    @Schema(description = "id")
    long id;
    @Schema(description = "제출 그룹")
    GroupMatchingResponse group;
    @Schema(description = "그룹 생성 시간")
    LocalDateTime startTime; //그룹의 생성 시간
    @Schema(description = "레포트 제출 시간")
    LocalDateTime endTime;  //레포트 제출(생성) 시간
    @Schema(description = "파일")
    String file;
    @Schema(description = "내용")
    String content;
    @Schema(description = "승인 상태")
    ReportType acceptStatus;
}
