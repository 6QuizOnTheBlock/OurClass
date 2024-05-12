package com.sixkids.domain.repository

import com.sixkids.domain.sse.SseEventListener

interface SseRepository {
    suspend fun connectSse()

    fun setEventListener(listener: SseEventListener)
}
