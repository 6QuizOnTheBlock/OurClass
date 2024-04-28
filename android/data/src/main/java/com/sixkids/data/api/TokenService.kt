package com.sixkids.data.api

import com.sixkids.data.model.request.RefreshTokenRequest
import com.sixkids.data.model.response.TokenResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("members/token")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): ApiResult<TokenResponse>
}