package com.sixkids.data.repository.relay.remote

import com.sixkids.model.RunningRelay

interface RelayRemoteDataSource {
    suspend fun getRunningRelay(organizationId: Long) : RunningRelay
}