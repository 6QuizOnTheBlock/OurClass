package com.sixkids.domain.usecase.relay

import com.sixkids.domain.repository.RelayRepository
import javax.inject.Inject

class GetRelayHistoryUseCase @Inject constructor(
    private val relayRepository: RelayRepository)
{

    suspend operator fun invoke(
        organizationId: Int,
        memberId: Int? = null
    ) = relayRepository.getRelayHistory(organizationId, memberId)
}