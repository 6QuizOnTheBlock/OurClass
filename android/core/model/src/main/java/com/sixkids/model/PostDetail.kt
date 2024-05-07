package com.sixkids.model

data class PostDetail(
    val writeMember: MemberSimple,
    val createTime: String,
    val title: String,
    val content: String,
    val ImageUri: String,
    val comments: List<Comment>,
)
