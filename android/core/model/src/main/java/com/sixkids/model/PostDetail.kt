package com.sixkids.model

import java.time.LocalDateTime

data class PostDetail(
    val writeMember: MemberSimple = MemberSimple(0, "", ""),
    val createTime: LocalDateTime = LocalDateTime.now(),
    val title: String = "",
    val content: String = "",
    val imageUri: String = "",
    val comments: List<Comment> = listOf(),
)
