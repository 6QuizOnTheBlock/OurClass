package com.sixkids.data.api

import com.sixkids.data.model.request.ChatFilterRequest
import com.sixkids.data.model.response.ChattingFilterListResponse
import com.sixkids.data.model.response.ChattingFilterResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatFilterService {
    @GET("filters")
    suspend fun getChatFilters(
        @Query("organizationId") organizationId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ApiResult<ApiResponse<ChattingFilterListResponse>>

    @DELETE("filters/{id}")
    suspend fun deleteChatFilter(
        @Query("id") id: Long,
    ): ApiResult<ApiResponse<Boolean>>

    @POST("filters/{organizationId}")
    suspend fun createChatFilter(
        @Query("organizationId") organizationId: Long,
        @Body badWord: ChatFilterRequest,
    ): ApiResult<ApiResponse<Long>>

    @PATCH("filters/{organizationId}/{id}")
    suspend fun updateChatFilter(
        @Query("organizationId") organizationId: Long,
        @Query("id") id: Long,
        @Body badWord: ChatFilterRequest,
    ): ApiResult<ApiResponse<Boolean>>
}