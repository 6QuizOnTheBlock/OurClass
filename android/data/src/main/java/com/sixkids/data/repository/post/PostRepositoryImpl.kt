package com.sixkids.data.repository.post

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sixkids.data.api.PostService
import com.sixkids.data.model.response.toModel
import com.sixkids.data.repository.post.remote.PostListPagingSource
import com.sixkids.data.repository.post.remote.PostRemoteDataSource
import com.sixkids.domain.repository.PostRepository
import com.sixkids.model.Post
import com.sixkids.model.PostDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource,
    private val postService: PostService
) : PostRepository {
    override suspend fun getPosts(
        organizationId: Int,
        memberId: Int,
        postCategory: String
    ): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(PostListPagingSource.DEFAULT_SIZE),
            pagingSourceFactory = {
                PostListPagingSource(
                    postService,
                    organizationId,
                    memberId,
                    postCategory
                )
            }
        ).flow.map { pagingData ->
            pagingData.map {
                postResponse -> postResponse.toModel()
            }
        }
    }

    override suspend fun createPost(
        organizationId: Long,
        title: String,
        content: String,
        secretStatus: Boolean,
        postCategory: String,
        file: File?
    ): Long {
        return postRemoteDataSource.createPost(
            organizationId,
            title,
            content,
            secretStatus,
            postCategory,
            file
        )
    }

    override suspend fun getPostDetail(postId: Long): PostDetail {
        return postRemoteDataSource.getPostDetail(postId).toModel()
    }

    override suspend fun deletePost(postId: Long): Boolean {
        return postRemoteDataSource.deletePost(postId)
    }

    override suspend fun updatePost(
        postId: Long,
        title: String,
        content: String,
        secretStatus: Boolean,
        postCategory: String,
        file: File?
    ): Long {
        return postRemoteDataSource.updatePost(
            postId,
            title,
            content,
            secretStatus,
            postCategory,
            file
        )
    }

    override suspend fun reportPost(postId: Long): Boolean {
        return postRemoteDataSource.reportPost(postId)
    }
}