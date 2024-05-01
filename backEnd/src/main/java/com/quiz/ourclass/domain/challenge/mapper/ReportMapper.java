package com.quiz.ourclass.domain.challenge.mapper;

import com.quiz.ourclass.domain.challenge.dto.request.ReportRequest;
import com.quiz.ourclass.domain.challenge.entity.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {

    @Mapping(target = "file", ignore = true)
    Report reportRequestToReport(ReportRequest reportRequest);
}
