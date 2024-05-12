package com.sixkids.data.repository.relay

import com.sixkids.data.repository.relay.remote.RelayRemoteDataSource
import com.sixkids.domain.repository.RelayRepository
import com.sixkids.model.RunningRelay
import javax.inject.Inject

class RelayRepositoryImpl @Inject constructor(
    private val relayRemoteDataSource: RelayRemoteDataSource
) : RelayRepository{
    override suspend fun getRunningRelay(organizationId: Long): RunningRelay {
        return relayRemoteDataSource.getRunningRelay(organizationId)
    }

}