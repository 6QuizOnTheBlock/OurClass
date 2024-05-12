package com.sixkids.data.repository.sse

import com.sixkids.data.repository.sse.remote.SseDataSource
import com.sixkids.domain.repository.SseRepository
import javax.inject.Inject

class SseRepositoryImpl @Inject constructor(
    private val sseDataSource: SseDataSource
): SseRepository {
    override suspend fun connectSse() = sseDataSource.connectSse()
}
