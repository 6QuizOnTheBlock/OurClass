package com.sixkids.data.repository.relay.remote

import com.sixkids.model.RelayDetail
import com.sixkids.model.RelayReceive
import com.sixkids.model.RelaySend
import com.sixkids.model.RunningRelay

interface RelayRemoteDataSource {
    suspend fun getRunningRelay(organizationId: Long) : RunningRelay

    suspend fun getRelayDetail(relayId: Long) : RelayDetail

    suspend fun createRelay(organizationId: Int, question: String) : Long

    suspend fun getRelayQuestion(relayId: Long) : String

    suspend fun receiveRelay(relayId: Int, senderId: Long, question: String) : RelayReceive

    suspend fun sendRelay(relayId: Int) : RelaySend
}