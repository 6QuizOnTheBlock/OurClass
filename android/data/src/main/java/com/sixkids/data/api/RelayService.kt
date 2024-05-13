package com.sixkids.data.api

import com.sixkids.data.model.request.RelayCreateRequest
import com.sixkids.data.model.response.RelayDetailResponse
import com.sixkids.data.model.response.RelayHistoryResponse
import com.sixkids.data.model.response.RunningRelayResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RelayService {

    @GET("relays")
    suspend fun getRelayHistory(
        @Query("orgId") organizationId: Int,
        @Query("memberId") memberId: Int? = null,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ApiResult<ApiResponse<RelayHistoryResponse>>

    @GET("relays/running")
    suspend fun getRunningRelay(
        @Query("organizationId") organizationId: Long
    ): ApiResult<ApiResponse<RunningRelayResponse>>

    @GET("relays")
    suspend fun getRelayDetail(
        @Query("id") relayId: Long
    ): ApiResult<ApiResponse<RelayDetailResponse>>

    @POST("relays")
    suspend fun createRelay(
        @Body relayCreateRequest: RelayCreateRequest
    ): ApiResult<ApiResponse<Long>>

    @GET("relays/{id}/question")
    suspend fun getRelayQuestion(
        @Path("id") relayId: Long
    ): ApiResult<ApiResponse<String>>

}