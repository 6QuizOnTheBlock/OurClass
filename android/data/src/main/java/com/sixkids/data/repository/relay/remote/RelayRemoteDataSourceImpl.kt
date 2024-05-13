package com.sixkids.data.repository.relay.remote

import com.sixkids.data.api.RelayService
import com.sixkids.data.model.request.RelayCreateRequest
import com.sixkids.data.model.response.toModel
import com.sixkids.model.RelayDetail
import com.sixkids.model.RunningRelay
import javax.inject.Inject

class RelayRemoteDataSourceImpl @Inject constructor(
    private val relayService: RelayService
) : RelayRemoteDataSource{
    override suspend fun getRunningRelay(organizationId: Long) : RunningRelay =
        relayService.getRunningRelay(organizationId).getOrThrow().data.toModel()

    override suspend fun getRelayDetail(relayId: Long): RelayDetail {
        return relayService.getRelayDetail(relayId).getOrThrow().data.toModel()
    }

    override suspend fun createRelay(organizationId: Int, question: String): Long {
        return relayService.createRelay(RelayCreateRequest(organizationId, question)).getOrThrow().data
    }

    override suspend fun getRelayQuestion(relayId: Long): String {
        return relayService.getRelayQuestion(relayId).getOrThrow().data
    }

}