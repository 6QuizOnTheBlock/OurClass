package com.sixkids.data.repository.sse.remote

import com.launchdarkly.eventsource.background.BackgroundEventSource
import javax.inject.Inject

class SseDataSourceImpl @Inject constructor(
    private val eventSource: BackgroundEventSource
): SseDataSource{
    override fun connectSse() = eventSource.start()
}
