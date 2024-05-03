package com.sixkids.model

data class PostItem(
    val id: Long,
    val title: String,
    val writer: String,
    val time: String,
    val commentCount: Int
)
