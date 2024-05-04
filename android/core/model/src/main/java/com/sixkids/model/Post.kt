package com.sixkids.model

data class Post(
    val id: Long,
    val title: String,
    val writer: String,
    val time: String,
    val commentCount: Int
)
