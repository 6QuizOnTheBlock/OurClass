package com.sixkids.data.api

import com.sixkids.data.model.response.ApiResponse
import com.sixkids.data.model.response.MemberInfoResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.GET

interface MemberService {
    @GET("members/")
    suspend fun getMemberInfo(): ApiResult<ApiResponse<MemberInfoResponse>>
}