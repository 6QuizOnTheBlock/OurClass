package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.domain.challenge.entity.ReportType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    long id;
    GroupMatchingResponse group;
    LocalDateTime startTime; //그룹의 생성 시간
    LocalDateTime endTime;  //레포트 제출(생성) 시간
    String file;
    String content;
    ReportType acceptStatus;
}
