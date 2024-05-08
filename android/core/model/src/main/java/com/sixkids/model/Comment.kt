package com.sixkids.model

data class Comment(
    val id: Long,
    val member: MemberSimple,
    val content: String,
    val createTime: String,
    val updateTime: String,
    val recomments: List<Recomment>,
)

data class Recomment(
    val id: Long,
    val member: MemberSimple,
    val content: String,
    val createTime: String,
    val updateTime: String,
    val parentId: Long,
)
