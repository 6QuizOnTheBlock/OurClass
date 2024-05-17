package com.sixkids.domain.usecase.relay

import com.sixkids.domain.repository.RelayRepository
import javax.inject.Inject

class SendRelayUseCase @Inject constructor(
    private val relayRepository: RelayRepository
){
    suspend operator fun invoke(relayId: Int) = runCatching {
        relayRepository.sendRelay(relayId)
    }
}