package com.sixkids.domain.repository

interface SseRepository {
    suspend fun connectSse()
}
