package com.sixkids.data.model.response

data class ChatResponse(
    val roomId: Long,
    val hasNext: Boolean,
    val messages: List<MessageResponse>
)

