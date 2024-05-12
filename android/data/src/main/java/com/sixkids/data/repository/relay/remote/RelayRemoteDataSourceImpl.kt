package com.sixkids.data.repository.relay.remote

import com.sixkids.data.api.RelayService
import com.sixkids.data.model.response.toModel
import com.sixkids.model.RunningRelay
import javax.inject.Inject

class RelayRemoteDataSourceImpl @Inject constructor(
    private val relayService: RelayService
) : RelayRemoteDataSource{
    override suspend fun getRunningRelay(organizationId: Long) : RunningRelay =
        relayService.getRunningRelay(organizationId).getOrThrow().data.toModel()

}