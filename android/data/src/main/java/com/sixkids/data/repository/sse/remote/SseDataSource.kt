package com.sixkids.data.repository.sse.remote

import com.sixkids.domain.sse.SseEventListener

interface SseDataSource {

    fun connectSse()

    fun setEventListener(listener: SseEventListener)
}
