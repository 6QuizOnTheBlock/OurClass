package com.sixkids.model

data class Chat(
    val memberId: Long,
    val memberName: String,
    val memberImageUrl: String,
    val content: String,
    val sendDateTime: Long,
    val readCount: Int = 1
)
