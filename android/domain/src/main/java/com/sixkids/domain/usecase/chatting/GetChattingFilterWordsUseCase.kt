package com.sixkids.domain.usecase.chatting

import com.sixkids.domain.repository.ChattingFilterRepository
import javax.inject.Inject

class GetChattingFilterWordsUseCase @Inject constructor(
    private val chattingFilterRepository: ChattingFilterRepository
) {
    suspend operator fun invoke(
        organizationId: Int
    ) = chattingFilterRepository.getChattingFilters(
        organizationId
    )
}