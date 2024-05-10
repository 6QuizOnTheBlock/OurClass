package com.sixkids.data.repository.comment.remote

import com.sixkids.data.api.CommentService
import com.sixkids.data.model.request.NewCommentRequest
import com.sixkids.data.model.request.UpdateCommentRequest
import javax.inject.Inject

class CommentRemoteDataSourceImpl @Inject constructor(
    private val commentService: CommentService
) : CommentRemoteDataSource{
    override suspend fun createComment(postId: Long, content: String, parentId: Long): Long {
        return commentService.createComment(NewCommentRequest(postId, content, parentId)).getOrThrow().data
    }

    override suspend fun deleteComment(id: Long): Boolean {
        return commentService.deleteComment(id).getOrThrow().data
    }

    override suspend fun updateComment(id: Long, content: String): Long {
        return commentService.updateComment(id, UpdateCommentRequest(id, content)).getOrThrow().data
    }

    override suspend fun reportComment(id: Long): Boolean {
        return commentService.reportComment(id).getOrThrow().data
    }
}