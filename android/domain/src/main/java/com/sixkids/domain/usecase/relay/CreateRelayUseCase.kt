package com.sixkids.domain.usecase.relay

import com.sixkids.domain.repository.RelayRepository
import javax.inject.Inject

class CreateRelayUseCase @Inject constructor(
    private val relayRepository: RelayRepository
){
    suspend operator fun invoke(organizationId: Int, question: String) = runCatching {
        relayRepository.createRelay(organizationId, question)
    }
}