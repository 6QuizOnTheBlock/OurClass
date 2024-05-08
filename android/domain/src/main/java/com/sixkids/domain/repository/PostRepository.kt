package com.sixkids.domain.repository

import androidx.paging.PagingData
import com.sixkids.model.Post
import com.sixkids.model.PostDetail
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

    suspend fun getPosts(
        organizationId: Int,
        memberId: Int? = null,
        postCategory: String,
    ): Flow<PagingData<Post>>

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
    ): PostDetail

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