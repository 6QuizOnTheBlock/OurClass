package com.quiz.ourclass.domain.chat.mapper;

import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;
import com.quiz.ourclass.domain.chat.entity.ChatFilter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatFilterMapper {

    void updateChatFilterFromRequest(
        ChatFilterRequest request,
        @MappingTarget ChatFilter chatFilter
    );
}
