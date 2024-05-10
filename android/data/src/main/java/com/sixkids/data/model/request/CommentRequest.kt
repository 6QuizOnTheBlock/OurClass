package com.sixkids.data.model.request

data class NewCommentRequest(
    val boardId: Long,
    val content: String,
    val parentId: Long,
)


data class UpdateCommentRequest(
    val id: Long,
    val content: String,
)