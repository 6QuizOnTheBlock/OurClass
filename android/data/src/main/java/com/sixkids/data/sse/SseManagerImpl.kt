package com.sixkids.data.sse

import com.sixkids.data.repository.sse.remote.SseDataSource
import com.sixkids.domain.sse.SseEventListener
import com.sixkids.domain.sse.SseManager
import javax.inject.Inject

class SseManagerImpl @Inject constructor(
    private val sseDataSource: SseDataSource
): SseManager {
    override fun connect() = sseDataSource.connectSse()

    override fun setEventListener(listener: SseEventListener) {
        sseDataSource.setEventListener(listener)
    }

}
