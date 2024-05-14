package com.sixkids.data.repository.relay

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sixkids.data.api.RelayService
import com.sixkids.data.repository.relay.remote.RelayHistoryPagingSource
import com.sixkids.data.repository.relay.remote.RelayRemoteDataSource
import com.sixkids.domain.repository.RelayRepository
import com.sixkids.model.Relay
import com.sixkids.model.RelayDetail
import com.sixkids.model.RelayReceive
import com.sixkids.model.RunningRelay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RelayRepositoryImpl @Inject constructor(
    private val relayService: RelayService,
    private val relayRemoteDataSource: RelayRemoteDataSource
) : RelayRepository{

    override suspend fun getRelayHistory(
        organizationId: Int,
        memberId: Int?,
    ): Flow<PagingData<Relay>> =
        Pager(
            config = PagingConfig(RelayHistoryPagingSource.DEFAULT_SIZE),
            pagingSourceFactory = {
                RelayHistoryPagingSource(
                    relayService,
                    organizationId,
                    memberId,
                )
            }
        ).flow

    override suspend fun getRunningRelay(organizationId: Long): RunningRelay {
        return relayRemoteDataSource.getRunningRelay(organizationId)
    }

    override suspend fun getRelayDetail(relayId: Long): RelayDetail {
        return relayRemoteDataSource.getRelayDetail(relayId)
    }

    override suspend fun createRelay(organizationId: Int, question: String): Long {
        return relayRemoteDataSource.createRelay(organizationId, question)
    }

    override suspend fun getRelayQuestion(relayId: Long): String {
        return relayRemoteDataSource.getRelayQuestion(relayId)
    }

    override suspend fun receiveRelay(
        relayId: Int,
        senderId: Long,
        question: String
    ): RelayReceive {
        return relayRemoteDataSource.receiveRelay(relayId, senderId, question)
    }


}