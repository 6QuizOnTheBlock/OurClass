package com.sixkids.data.api

import com.sixkids.data.model.request.NewPostRequest
import com.sixkids.data.model.response.PostDetailResponse
import com.sixkids.data.model.response.PostListResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {

    @GET("boards")
    suspend fun getPosts(
        @Query("organizationId") organizationId: Int,
        @Query("memberId") memberId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("postCategory") postCategory: String,
    ): ApiResult<ApiResponse<PostListResponse>>

    @POST("boards")
    suspend fun createPost(
        @Query("organizationId") organizationId: Long,
        @Part("request") postRequestBody: NewPostRequest,
        @Part file: MultipartBody.Part?,
    ): ApiResult<ApiResponse<Long>>

    @GET("boards/{id}")
    suspend fun getPostDetail(
        @Path("id") postId: Long,
    ): ApiResult<ApiResponse<PostDetailResponse>>

    @DELETE("boards/{id}")
    suspend fun deletePost(
        @Path("id") postId: Long,
    ): ApiResult<ApiResponse<Boolean>>

    @PATCH("boards/{id}")
    suspend fun updatePost(
        @Path("id") postId: Long,
        @Part("request") postRequestBody: NewPostRequest,
        @Part file: MultipartBody.Part?,
    ): ApiResult<ApiResponse<Long>>

    @POST("boards/{id}/report")
    suspend fun reportPost(
        @Path("id") postId: Long,
    ): ApiResult<ApiResponse<Boolean>>
}