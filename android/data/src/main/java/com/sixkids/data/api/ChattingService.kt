package com.sixkids.data.api

import com.sixkids.data.model.response.ChatResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ChattingService {
    @GET("/chats")
    suspend fun getChatList(
        @Query("roomId") roomId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
        ): ApiResult<ApiResponse<ChatResponse>>
}