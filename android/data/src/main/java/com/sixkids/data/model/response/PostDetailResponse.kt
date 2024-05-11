package com.sixkids.data.model.response

import com.sixkids.model.Comment
import com.sixkids.model.PostDetail
import com.sixkids.model.Recomment
import com.squareup.moshi.Json
import java.time.LocalDateTime

data class PostDetailResponse(
    @Json(name = "member")
    val writer: MemberSimpleInfoResponse,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime?,
    val title: String,
    val content: String,
    @Json(name = "path")
    val photoUrl: String?,
    val comments: List<CommentResponse>,
)

data class CommentResponse(
    val id: Long,
    @Json(name = "member")
    val writer: MemberSimpleInfoResponse,
    val content: String,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime?,
    @Json(name = "children")
    val recommentList: List<RecommentResponse>,
)

data class RecommentResponse(
    val id: Long,
    @Json(name = "member")
    val writer: MemberSimpleInfoResponse,
    val content: String,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime?,
    val parentId: Long,
)

fun PostDetailResponse.toModel(): PostDetail {
    return PostDetail(
        createTime = createTime,
        title = title,
        content = content,
        imageUri = photoUrl ?: "",
        comments = comments.map { it.toModel() },
        writeMember = writer.toModel()
    )
}

fun CommentResponse.toModel(): Comment{
    return Comment(
        id = id,
        content = content,
        createTime = createTime,
        updateTime = updateTime,
        member = writer.toModel(),
        recomments = recommentList.map { it.toModel() }
    )
}

fun RecommentResponse.toModel(): Recomment {
    return Recomment(
        id = id,
        content = content,
        createTime = createTime,
        updateTime = updateTime,
        parentId = parentId,
        member = writer.toModel()
    )
}
