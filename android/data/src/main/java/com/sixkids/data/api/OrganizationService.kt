package com.sixkids.data.api

import com.sixkids.data.model.response.ApiResponse
import com.sixkids.data.model.response.OrganizationResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.GET

interface OrganizationService {
    @GET("/organizations")
    suspend fun getOrganizationList(): ApiResult<ApiResponse<List<OrganizationResponse>>>
}