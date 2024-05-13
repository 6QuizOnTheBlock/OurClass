package com.sixkids.data.model.response

import com.sixkids.model.MemberSimple
import com.sixkids.model.MemberSimpleWithScore

data class StudentResponse(
    val id: Long,
    val name: String,
    val photo: String,
)

data class StudentWithRelationScoreResponse(
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
