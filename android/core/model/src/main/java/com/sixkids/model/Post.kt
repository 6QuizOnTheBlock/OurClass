package com.sixkids.model

import java.time.LocalDateTime

data class Post(
    val id: Long,
    val title: String,
    val writer: String,
    val time: LocalDateTime,
    val commentCount: Int
)
