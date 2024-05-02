package com.sixkids.data.api

import com.sixkids.data.model.request.SignInRequest
import com.sixkids.data.model.response.ApiResponse
import com.sixkids.data.model.response.SignInResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {

    @POST("members/sign-in")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): ApiResult<ApiResponse<SignInResponse>>
}