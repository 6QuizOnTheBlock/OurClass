package com.quiz.ourclass.domain.chat.service;

import com.quiz.ourclass.domain.chat.dto.request.ChatFilterRequest;
import com.quiz.ourclass.domain.chat.mapper.ChatFilterMapper;
import com.quiz.ourclass.domain.chat.repository.ChatFilterRepository;
import com.quiz.ourclass.domain.organization.entity.Organization;
import com.quiz.ourclass.domain.organization.repository.OrganizationRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatFilterServiceImpl implements ChatFilterService {

    private final OrganizationRepository organizationRepository;
    private final ChatFilterRepository chatFilterRepository;
    private final ChatFilterMapper chatFilterMapper;

    @Override
    public Long register(Long organizationId, ChatFilterRequest request) {
        Organization organization = organizationRepository.findById(organizationId)
            .orElseThrow(() -> new GlobalException(ErrorCode.ORGANIZATION_NOT_FOUND));

        return chatFilterRepository.save(
            chatFilterMapper.ChatFilterRegisterToChatFilter(organization, request)
        ).getId();
    }
}
