package com.sixkids.model

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val member: MemberSimple,
    val content: String,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val recomments: List<Recomment>,
)

data class Recomment(
    val id: Long,
    val member: MemberSimple,
    val content: String,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val parentId: Long,
)
