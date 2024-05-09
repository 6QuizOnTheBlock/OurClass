package com.sixkids.model

import java.time.LocalDateTime

data class PostDetail(
    val writeMember: MemberSimple,
    val createTime: LocalDateTime,
    val title: String,
    val content: String,
    val ImageUri: String = "",
    val comments: List<Comment> = listOf(),
)
