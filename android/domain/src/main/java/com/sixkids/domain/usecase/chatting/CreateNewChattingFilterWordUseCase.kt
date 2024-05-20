package com.sixkids.domain.usecase.chatting

import com.sixkids.domain.repository.ChattingFilterRepository
import javax.inject.Inject

class CreateNewChattingFilterWordUseCase @Inject constructor(
    private val chattingFilterRepository: ChattingFilterRepository
)
{
    suspend operator fun invoke(
        organizationId: Long,
        badWord: String
    ) = runCatching {
        chattingFilterRepository.createChatFilter(organizationId, badWord)
    }
}