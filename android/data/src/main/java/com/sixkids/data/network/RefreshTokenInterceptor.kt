package com.sixkids.data.network

import com.sixkids.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RefreshTokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val REFRESH_HEADER = "refreshToken"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val atk = runBlocking {
            tokenRepository.getAccessToken()
        }
        val rtk = runBlocking {
            tokenRepository.getRefreshToken()
        }
        val request = chain.request().newBuilder().apply {
            addHeader(AUTHORIZATION_HEADER, "$TOKEN_TYPE $atk")
            addHeader(AUTHORIZATION_HEADER, "$REFRESH_HEADER $rtk")
        }

        return chain.proceed(request.build())
    }
}