package com.sixkids.data.repository.post.remote

import com.sixkids.data.api.PostService
import com.sixkids.data.model.request.NewPostRequest
import com.sixkids.data.model.response.PostDetailResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(
    private val postService: PostService,
): PostRemoteDataSource {
    override suspend fun createPost(
        organizationId: Long,
        title: String,
        content: String,
        secretStatus: Boolean,
        postCategory: String,
        file: File?
    ): Long {
        val image = file?.asRequestBody("image/jpg".toMediaTypeOrNull())
        val multipartImage = image?.let {
            MultipartBody.Part.createFormData("file", file.name, it)
        }

        val request = NewPostRequest(
            title = title,
            content = content,
            secretStatus = secretStatus,
            postCategory = postCategory,
        )

        return postService.createPost(organizationId, request, multipartImage).getOrThrow().data
    }

    override suspend fun getPostDetail(postId: Long): PostDetailResponse {
        return postService.getPostDetail(postId).getOrThrow().data
    }

    override suspend fun deletePost(postId: Long): Boolean {
        return postService.deletePost(postId).getOrThrow().data
    }

    override suspend fun updatePost(
        postId: Long,
        title: String,
        content: String,
        secretStatus: Boolean,
        postCategory: String,
        file: File?
    ): Long {
        val image = file?.asRequestBody("image/jpg".toMediaTypeOrNull())
        val multipartImage = image?.let {
            MultipartBody.Part.createFormData("file", file.name, it)
        }

        val request = NewPostRequest(
            title = title,
            content = content,
            secretStatus = secretStatus,
            postCategory = postCategory,
        )

        return postService.updatePost(postId, request, multipartImage).getOrThrow().data
    }

    override suspend fun reportPost(postId: Long): Boolean {
        return postService.reportPost(postId).getOrThrow().data
    }
}