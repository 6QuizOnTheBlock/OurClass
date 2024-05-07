package com.sixkids.data.api

import com.sixkids.data.model.request.NewPostRequest
import com.sixkids.data.model.response.PostDetailResponse
import com.sixkids.data.model.response.PostListResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {

    @GET("boards")
    fun getBoards(
        @Query("organizationId") organizationId: Int,
        @Query("memberId") memberId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("postCategory") postCategory: String,
    ): ApiResult<ApiResponse<PostListResponse>>

    @POST("boards")
    fun createPost(
        @Query("organizationId") organizationId: Long,
        @Part("request") postRequestBody: NewPostRequest,
        @Part file: MultipartBody.Part?,
    ): ApiResult<ApiResponse<Long>>

    @GET("boards/{id}")
    fun getPostDetail(
        @Path("id") postId: Long,
    ): ApiResult<ApiResponse<PostDetailResponse>>

}