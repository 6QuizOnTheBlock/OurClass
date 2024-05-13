package com.sixkids.data.model.response

import com.sixkids.model.StudentHomeInfo
import com.squareup.moshi.Json

data class StudentHomeResponse(
    val name: String,
    val photo: String,
    @Json(name = "organizationName")
    val className: String,
    val exp: Int,
    val notifyCount: Int,
    val relations: List<StudentWithRelationScoreResponse>,
)

internal fun StudentHomeResponse.toModel() = StudentHomeInfo(
    name = name,
    photo = photo,
    className = className,
    exp = exp,
    notifyCount = notifyCount,
    relations = relations.map { it.toModel() },
)