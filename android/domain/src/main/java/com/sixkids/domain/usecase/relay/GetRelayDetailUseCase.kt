package com.sixkids.domain.usecase.relay

import com.sixkids.domain.repository.RelayRepository
import javax.inject.Inject

class GetRelayDetailUseCase @Inject constructor(
    private val relayRepository: RelayRepository
){
    suspend operator fun invoke(relayId: Long) = runCatching {
        relayRepository.getRelayDetail(relayId)
    }
}