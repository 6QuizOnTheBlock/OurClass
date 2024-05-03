package com.sixkids.data.api

import com.sixkids.data.model.request.SignInRequest
import com.sixkids.data.model.response.ApiResponse
import com.sixkids.data.model.response.SignInResponse
import com.sixkids.data.network.ApiResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface SignInService {

    @POST("members/sign-in")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): ApiResult<ApiResponse<SignInResponse>>

    @Multipart
    @POST("members")
    suspend fun signUp(
        @Part file: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
    ): ApiResult<ApiResponse<SignInResponse>>
}