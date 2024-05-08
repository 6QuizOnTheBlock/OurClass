package com.sixkids.data.model.response

import com.sixkids.model.UserInfo

data class MemberInfoResponse(
    val id: Int,
    val name: String,
    val email: String,
    val photo: String,
    val role: String
)

internal fun MemberInfoResponse.toModel(): UserInfo {
    return UserInfo(
        id = id,
        name = name,
        email = email,
        photo = photo,
        role = role
    )
}