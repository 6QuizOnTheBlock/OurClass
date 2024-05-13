package com.sixkids.data.model.response

import com.sixkids.model.MemberSimple
import com.sixkids.model.MemberSimpleWithScore
import com.squareup.moshi.Json

data class StudentResponse(
    val id: Long,
    val name: String,
    val photo: String,
)

data class StudentWithRelationScoreResponse(
    @Json(name = "member")
    val student: StudentResponse,
    val relationPoint: Int,
)

internal fun StudentResponse.toModel() = MemberSimple(
    id = id,
    name = name,
    photo = photo,
)

internal fun StudentWithRelationScoreResponse.toModel() = MemberSimpleWithScore(
    memberSimple = student.toModel(),
    relationPoint = relationPoint,
)
