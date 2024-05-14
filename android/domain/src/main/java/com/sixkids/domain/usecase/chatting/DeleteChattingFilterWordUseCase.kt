package com.sixkids.domain.usecase.chatting

import com.sixkids.domain.repository.ChattingFilterRepository
import javax.inject.Inject

class DeleteChattingFilterWordUseCase @Inject constructor(
    private val chattingFilterRepository: ChattingFilterRepository
){
    suspend operator fun invoke(
        chattingFilterId: Long
    ) = chattingFilterRepository.deleteChatFilter(
        chattingFilterId
    )
}