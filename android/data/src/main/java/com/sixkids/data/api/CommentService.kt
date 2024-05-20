package com.sixkids.data.api

import com.sixkids.data.model.request.NewCommentRequest
import com.sixkids.data.model.request.UpdateCommentRequest
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {
    @POST("comments")
    suspend fun createComment(
        @Body request: NewCommentRequest
    ): ApiResult<ApiResponse<Long>>

    @DELETE("comments/{id}")
    suspend fun deleteComment(
        @Path("id") id: Long
    ): ApiResult<ApiResponse<Boolean>>

    @PATCH("comments/{id}")
    suspend fun updateComment(
        @Path("id") id: Long,
        @Body request: UpdateCommentRequest
    ): ApiResult<ApiResponse<Long>>

    @POST("comments/{id}/report")
    suspend fun reportComment(
        @Path("id") id: Long
    ): ApiResult<ApiResponse<Boolean>>
}