package com.sixkids.domain.sse

interface SseManager {
    fun connect()
    fun setEventListener(listener: SseEventListener)
}
