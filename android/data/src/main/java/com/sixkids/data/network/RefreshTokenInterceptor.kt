package com.sixkids.data.network

import com.sixkids.data.datastore.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RefreshTokenInterceptor @Inject constructor(
    private val tokenDataStore: TokenDataStore
): Interceptor {
    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenDataStore.getRefreshToken()
        }
        val request = chain.request().newBuilder().apply {
            addHeader(AUTHORIZATION_HEADER, "$TOKEN_TYPE $token")
        }

        return chain.proceed(request.build())
    }
}