package com.quiz.ourclass.domain.challenge.dto.response;

import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "그룹 조회 응답 DTO")
public class GroupMatchingResponse {

    @Schema(description = "id")
    long id;
    @Schema(description = "그룹 인원 수")
    int headCount;
    @Schema(description = "그룹장 id")
    long leaderId;
    @Schema(description = "그룹 참여 학생 리스트")
    List<MemberSimpleDTO> students;
}