package com.sixkids.data.api

import com.sixkids.data.model.request.JoinOrganizationRequest
import com.sixkids.data.model.request.NewOrganizationRequest
import com.sixkids.data.model.response.ApiResponse
import com.sixkids.data.model.response.ClassSummaryResponse
import com.sixkids.data.model.response.OrganizationInviteCodeResponse
import com.sixkids.data.model.response.OrganizationNameResponse
import com.sixkids.data.model.response.OrganizationMemberResponse
import com.sixkids.data.model.response.OrganizationResponse
import com.sixkids.data.model.response.RankResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OrganizationService {
    @GET("organizations")
    suspend fun getOrganizationList(): ApiResult<ApiResponse<List<OrganizationResponse>>>

    @POST("organizations")
    suspend fun newOrganization(@Body newOrganizationRequest: NewOrganizationRequest): ApiResult<ApiResponse<Long>>

    @POST("organizations/{id}/join")
    suspend fun joinOrganization(
        @Path(value = "id") orgId: Int,
        @Body joinOrganizationRequest: JoinOrganizationRequest
    ): ApiResult<ApiResponse<Long>>

    @GET("organizations/{id}/summary")
    suspend fun getOrganizationSummary(
        @Path(value = "id") organizationId: Int
    ): ApiResult<ApiResponse<ClassSummaryResponse>>

    @PATCH("organizations/{id}")
    suspend fun updateOrganization(
        @Path(value = "id") organizationId: Int,
        @Body newOrganizationRequest: NewOrganizationRequest
    ): ApiResult<ApiResponse<OrganizationNameResponse>>

    @GET("organizations/{id}/code")
    suspend fun getOrganizationInviteCode(
        @Path(value = "id") organizationId: Int
    ): ApiResult<ApiResponse<OrganizationInviteCodeResponse>>

    @GET("organizations/{id}/members")
    suspend fun getOrganizationMembers(
        @Path(value = "id") orgId: Int
    ): ApiResult<ApiResponse<List<OrganizationMemberResponse>>>

    @GET("organizations/{id}/rank")
    suspend fun getOrganizationRank(
        @Path(value = "id") orgId: Int
    ): ApiResult<ApiResponse<List<RankResponse>>>
}