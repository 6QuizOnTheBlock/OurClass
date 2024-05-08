package com.sixkids.data.model.response

import com.sixkids.model.MemberSimple

data class StudentResponse(
    val id: Long,
    val name: String,
    val photo: String,
)

internal fun StudentResponse.toModel() = MemberSimple(
    id = id,
    name = name,
    photo = photo,
)
