package com.sixkids.data.model.response

import com.squareup.moshi.Json
import java.time.LocalDateTime

data class PostListResponse(
    val page: Int,
    val size: Int,
    val hasNextPage: Boolean,
    val posts: List<PostResponse>
)

data class PostResponse(
    val id: Int,
    val title: String,
    val author: String,
    @Json(name = "CommentCount")
    val commentCount: Int,
    val createTime: LocalDateTime = LocalDateTime.now(),
)