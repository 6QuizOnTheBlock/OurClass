package com.sixkids.domain.repository

import com.sixkids.model.RunningRelay

interface RelayRepository {
    suspend fun getRunningRelay(organizationId: Long): RunningRelay
}