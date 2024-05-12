package com.sixkids.domain.usecase.relay

import com.sixkids.domain.repository.RelayRepository
import javax.inject.Inject

class GetRunningRelayUseCase @Inject constructor(
    private val relayRepository: RelayRepository
){
    suspend operator fun invoke(organizationId: Long) = runCatching {
        relayRepository.getRunningRelay(organizationId)
    }
}