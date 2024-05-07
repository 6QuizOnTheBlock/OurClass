package com.sixkids.data.model.response

import com.squareup.moshi.Json
import java.time.LocalDateTime

data class PostDetailResponse(
    @Json(name = "member")
    val writer: MemberSimpleInfoResponse,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val title: String,
    val content: String,
    @Json(name = "path")
    val photoUrl: String,
    val comments: List<CommentResponse>,
)

data class CommentResponse(
    val id: Long,
    @Json(name = "member")
    val writer: MemberSimpleInfoResponse,
    val content: String,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    @Json(name = "children")
    val recommentList: List<RecommentResponse>,
)

data class RecommentResponse(
    val id: Long,
    @Json(name = "member")
    val writer: MemberSimpleInfoResponse,
    val content: String,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val parentId: Long,
)