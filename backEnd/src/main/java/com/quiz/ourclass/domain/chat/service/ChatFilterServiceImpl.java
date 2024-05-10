package com.quiz.ourclass.domain.chat.service;

import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;
import com.quiz.ourclass.domain.chat.entity.ChatFilter;
import com.quiz.ourclass.domain.chat.mapper.ChatFilterMapper;
import com.quiz.ourclass.domain.chat.repository.ChatFilterRepository;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatFilterServiceImpl implements ChatFilterService {

    private final OrganizationRepository organizationRepository;
    private final ChatFilterRepository chatFilterRepository;
    private final ChatFilterMapper chatFilterMapper;

    @Transactional
    @Override
    public Long register(Long organizationId, ChatFilterRequest request) {
        Organization organization = organizationCheck(organizationId);
        duplicateWordCheck(organization, request.badWord());

        return chatFilterRepository.save(
            chatFilterMapper.RequestToChatFilter(organization, request.badWord())
        ).getId();
    }

    @Override
    public Boolean modify(Long organizationId, Long chatFilterId, ChatFilterRequest request) {
        Organization organization = organizationCheck(organizationId);
        duplicateWordCheck(organization, request.badWord());

        ChatFilter chatFilter = chatFilterRepository.findById(chatFilterId)
            .orElseThrow(() -> new GlobalException(ErrorCode.CHAT_FILTER_NOTFOUND_WORD));

        chatFilterRepository.save(
            chatFilterMapper.updateChatFilterFromRequest(request, chatFilter)
        );

        return true;
    }

    private Organization organizationCheck(Long organizationId) {
        return organizationRepository.findById(organizationId)
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));
    }

    private void duplicateWordCheck(Organization organization, String badWord) {
        if (chatFilterRepository.findByOrganizationAndBadWord(organization, badWord).isPresent()) {
            throw new GlobalException(ErrorCode.ALREADY_REGISTER_WORD);
        }
    }


}
