package com.sixkids.domain.usecase.chatting

import com.sixkids.domain.repository.ChattingRepository
import javax.inject.Inject

class GetChattingHistoryUseCase @Inject constructor(
    private val chattingRepository: ChattingRepository
){
    suspend operator fun invoke(roomId: Long) = chattingRepository.getChattingList(roomId)

}