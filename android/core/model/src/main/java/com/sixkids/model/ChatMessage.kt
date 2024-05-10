package com.sixkids.model

data class ChatMessage(
    val roomId: Long,
    val memberImageUrl: String,
    val content: String
)
