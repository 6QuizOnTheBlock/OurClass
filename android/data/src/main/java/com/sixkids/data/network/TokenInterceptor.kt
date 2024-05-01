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
        //TODO: 로그인 구현 전 까지는 직접 토큰을 넣어 줘야 동작
//        val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1IiwiaWF0IjoxNzE0NTQxNDY5LCJleHAiOjE3MTQ2Mjc4Njl9.c2PIwJ7llRGVJ79skEin0ylSiwECfuXY2v1o0aekLmHWNxibaBt2XcXEkaVsb41lBEJTdJmDWm0HVig_wTzQGw"
        val request = chain.request().newBuilder().apply {
            addHeader(AUTHORIZATION_HEADER, "$TOKEN_TYPE $token")
        }

        return chain.proceed(request.build())
    }

}
