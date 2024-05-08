package com.sixkids.data.repository.post.remote

import com.sixkids.data.model.response.PostDetailResponse
import java.io.File

interface PostRemoteDataSource {

    suspend fun createPost(
        organizationId: Long,
        title: String,
        content: String,
        secretStatus: Boolean,
        postCategory: String,
        file: File?,
    ): Long

    suspend fun getPostDetail(
        postId: Long,
    ): PostDetailResponse

    suspend fun deletePost(
        postId: Long,
    ): Boolean

    suspend fun updatePost(
        postId: Long,
        title: String,
        content: String,
        secretStatus: Boolean,
        postCategory: String,
        file: File?,
    ): Long

    suspend fun reportPost(
        postId: Long,
    ): Boolean

}