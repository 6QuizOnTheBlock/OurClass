package com.sixkids.data.model.response

import com.sixkids.model.Chat

data class ChatHistoryResponse(
    val roomId: Long,
    val hasNext: Boolean,
    val messages: List<MessageResponse>
)

data class MessageResponse(
    val memberId: Long,
    val memberName: String,
    val memberImageUrl: String,
    val content: String,
    val sendDateTime: Long,
    val readCount: Int
)

internal fun MessageResponse.toModel() : Chat {
    return Chat(
        memberId = memberId,
        memberName = memberName,
        memberImageUrl = memberImageUrl,
        content = content,
        sendDateTime = sendDateTime,
        readCount = readCount
    )
}