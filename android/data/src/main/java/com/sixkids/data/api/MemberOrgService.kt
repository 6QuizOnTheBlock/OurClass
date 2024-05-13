package com.sixkids.data.api

import com.sixkids.data.model.response.StudentHomeResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.GET

interface MemberOrgService {
    @GET("organizations/{id}/home")
    suspend fun getStudentHomeInfo(): ApiResult<ApiResponse<StudentHomeResponse>>
}