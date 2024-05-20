package com.sixkids.data.model.response

import com.sixkids.model.MemberSimple

data class OrganizationMemberResponse(
    val id: Int,
    val name: String,
    val photo: String
)

internal fun OrganizationMemberResponse.toModel() = MemberSimple(
    id = id.toLong(),
    name = name,
    photo = photo
)