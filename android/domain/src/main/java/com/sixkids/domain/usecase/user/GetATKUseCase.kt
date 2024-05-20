package com.sixkids.domain.usecase.user

import com.sixkids.domain.repository.TokenRepository
import javax.inject.Inject

class GetATKUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
){
    suspend operator fun invoke() = runCatching {
        tokenRepository.getAccessToken()
    }
}