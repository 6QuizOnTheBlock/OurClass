package com.sixkids.model

data class Chat(
    val id: String,
    val roomId: Long,
    val memberId: Long,
    val memberName: String,
    val memberProfilePhoto: String,
    val content: String,
    val sendDateTime: Long,
    val readCount: Int = 1
)
