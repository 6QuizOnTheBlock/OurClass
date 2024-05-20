package com.sixkids.data.network

import com.sixkids.data.api.TokenService
import com.sixkids.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val tokenService: TokenService
): Authenticator{

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val tokenResponse = tokenService.refreshToken()

            // 2-1. 정상적으로 받지 못하면 request token 까지 만료된 것.
            if(tokenResponse.isFailure || tokenResponse.getOrNull() == null) {
                tokenRepository.clearTokens()
                null
            } else {
                tokenRepository.saveIdToken(tokenResponse.getOrNull()!!.data.accessToken)
                tokenRepository.saveRefreshToken(tokenResponse.getOrNull()!!.data.refreshToken)
                // 3. 헤더에 토큰을 교체한 request 생성
                response.request.newBuilder()
                    .header(AUTHORIZATION_HEADER, "$TOKEN_TYPE ${tokenResponse.getOrNull()!!.data.accessToken}")
                    .build()
            }
        }
    }

}
