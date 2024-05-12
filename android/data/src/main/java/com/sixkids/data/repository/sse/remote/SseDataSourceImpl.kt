package com.sixkids.data.repository.sse.remote

import com.launchdarkly.eventsource.background.BackgroundEventSource
import com.sixkids.data.network.SseEventHandler
import com.sixkids.domain.sse.SseEventListener
import javax.inject.Inject

class SseDataSourceImpl @Inject constructor(
    private val eventSource: BackgroundEventSource,
    private val sseEventHandler: SseEventHandler,
): SseDataSource{
    override fun connectSse() = eventSource.start()
    override fun setEventListener(listener: SseEventListener) {
        sseEventHandler.setEventListener(listener)
    }

}
