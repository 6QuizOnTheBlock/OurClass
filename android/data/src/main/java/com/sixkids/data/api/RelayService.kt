package com.sixkids.data.api

import com.sixkids.data.model.response.RelayHistoryResponse
import com.sixkids.data.model.response.RunningRelayResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.GET
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
}