package com.sixkids.data.api

import com.sixkids.data.model.request.NewOrganizationRequest
import com.sixkids.data.model.response.ApiResponse
import com.sixkids.data.model.response.OrganizationResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrganizationService {
    @GET("organizations")
    suspend fun getOrganizationList(): ApiResult<ApiResponse<List<OrganizationResponse>>>

    @POST("organizations")
    suspend fun newOrganization(@Body newOrganizationRequest: NewOrganizationRequest): ApiResult<ApiResponse<Long>>
}