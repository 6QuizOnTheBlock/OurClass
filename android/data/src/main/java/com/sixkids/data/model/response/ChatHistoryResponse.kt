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
    val memberImageUrl: String?,
    val content: String,
    val sendDateTime: Long,
    val readCount: Int
)

internal fun MessageResponse.toModel() : Chat {
    return Chat(
        memberId = memberId,
        memberName = memberName,
        memberImageUrl = memberImageUrl?:"https://ulvanbucket.s3.ap-northeast-2.amazonaws.com/5001fcad-52b8-42ba-a587-c72797a3a36f_profile.jpg",
        content = content,
        sendDateTime = sendDateTime,
        readCount = readCount
    )
}