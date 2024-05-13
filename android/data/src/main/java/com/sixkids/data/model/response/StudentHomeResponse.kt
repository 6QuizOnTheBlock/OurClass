package com.sixkids.data.model.response

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