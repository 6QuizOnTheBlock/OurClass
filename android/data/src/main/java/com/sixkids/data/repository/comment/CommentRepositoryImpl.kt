package com.sixkids.data.repository.comment

import com.sixkids.data.repository.comment.remote.CommentRemoteDataSource
import com.sixkids.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentRemoteDataSource: CommentRemoteDataSource
) : CommentRepository {
    override suspend fun createComment(postId: Long, content: String, parentId: Long): Long {
        return commentRemoteDataSource.createComment(postId, content, parentId)
    }

    override suspend fun deleteComment(id: Long): Boolean {
        return commentRemoteDataSource.deleteComment(id)
    }

    override suspend fun updateComment(id: Long, content: String): Long {
        return commentRemoteDataSource.updateComment(id, content)
    }

    override suspend fun reportComment(id: Long): Boolean {
        return commentRemoteDataSource.reportComment(id)
    }
}