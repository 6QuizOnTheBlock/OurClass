package com.sixkids.domain.sse

import com.sixkids.model.SseEventType

interface SseEventListener {
    fun onEventReceived(event: SseEventType, data: String)
    fun onError(error: Throwable)
}
