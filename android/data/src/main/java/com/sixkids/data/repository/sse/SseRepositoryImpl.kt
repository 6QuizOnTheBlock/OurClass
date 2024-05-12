package com.sixkids.data.repository.sse

import com.sixkids.data.repository.sse.remote.SseDataSource
import com.sixkids.domain.repository.SseRepository
import com.sixkids.domain.sse.SseEventListener
import javax.inject.Inject

class SseRepositoryImpl @Inject constructor(
    private val sseDataSource: SseDataSource
): SseRepository {
    override suspend fun connectSse() = sseDataSource.connectSse()
    override fun setEventListener(listener: SseEventListener) {
        sseDataSource.setEventListener(listener)
    }
}
