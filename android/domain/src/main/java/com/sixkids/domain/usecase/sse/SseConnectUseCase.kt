package com.sixkids.domain.usecase.sse

import com.sixkids.domain.repository.SseRepository
import javax.inject.Inject

class SseConnectUseCase @Inject constructor(
    private val sseRepository: SseRepository
) {
    suspend operator fun invoke() = sseRepository.connectSse()
}
