package com.sixkids.domain.usecase.relay

import com.sixkids.domain.repository.RelayRepository
import javax.inject.Inject

class ReceiveRelayUseCase @Inject constructor(
    private val relayRepository: RelayRepository
){
    suspend operator fun invoke(
        relayId: Int,
        senderId: Long,
        question: String
    ) = runCatching {
        relayRepository.receiveRelay(relayId, senderId, question)
    }
}