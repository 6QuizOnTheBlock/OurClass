package com.quiz.ourclass.domain.chat.mapper;

import com.quiz.ourclass.domain.chat.dto.ChatFilterDTO;
import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;
import com.quiz.ourclass.domain.chat.dto.response.ChatFilterResponse;
import com.quiz.ourclass.domain.chat.entity.ChatFilter;
import com.quiz.ourclass.domain.organization.entity.Organization;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatFilterMapper {

    @Mapping(target = "id", ignore = true)
    ChatFilter RequestToChatFilter(Organization organization, String badWord);

    ChatFilter updateChatFilterFromRequest(
        ChatFilterRequest request,
        @MappingTarget ChatFilter chatFilter
    );

    ChatFilterDTO chatFilterToChatFilterDTO(ChatFilter chatFilter);

    ChatFilterResponse chatFilterDTOListToChatFilterResponse(
        List<ChatFilterDTO> words, boolean hasNext
    );
}
