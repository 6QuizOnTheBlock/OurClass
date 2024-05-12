package com.sixkids.domain.repository

import androidx.paging.PagingData
import com.sixkids.model.Relay
import com.sixkids.model.RelayDetail
import com.sixkids.model.RunningRelay
import kotlinx.coroutines.flow.Flow

interface RelayRepository {
    suspend fun getRelayHistory(
        organizationId: Int,
        memberId: Int?
    ): Flow<PagingData<Relay>>

    suspend fun getRunningRelay(organizationId: Long): RunningRelay

    suspend fun getRelayDetail(relayId: Long): RelayDetail
}