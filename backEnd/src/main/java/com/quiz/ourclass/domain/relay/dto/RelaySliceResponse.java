package com.quiz.ourclass.domain.relay.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "이어달리기 페이징 목록 응답 DTO")
public record RelaySliceResponse(
    @Schema(description = "페이지")
    int page,
    @Schema(description = "페이지 당 갯수")
    int size,
    @Schema(description = "마지막 페이지 여부")
    boolean last,
    @Schema(description = "이어달리기 목록")
    List<RelaySimpleDTO> relays
) {

}
