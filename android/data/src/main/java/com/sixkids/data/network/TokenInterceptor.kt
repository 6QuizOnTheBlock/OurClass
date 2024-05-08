package com.sixkids.data.network

import com.sixkids.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenRepository.getAccessToken()
        }
        val request = chain.request().newBuilder().apply {
            addHeader(AUTHORIZATION_HEADER, "$TOKEN_TYPE $token")
        }

        return chain.proceed(request.build())
    }

}
