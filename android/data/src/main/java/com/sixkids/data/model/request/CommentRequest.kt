package com.sixkids.data.model.request

data class NewCommentRequest(
    val postId: Long,
    val content: String,
    val parentId: Long,
)


data class UpdateCommentRequest(
    val id: Long,
    val content: String,
)