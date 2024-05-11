package com.sixkids.domain.repository

interface CommentRepository {
    suspend fun createComment(postId: Long, content: String, parentId: Long): Long

    suspend fun deleteComment(id: Long): Boolean

    suspend fun updateComment(id: Long, content: String): Long

    suspend fun reportComment(id: Long): Boolean
}