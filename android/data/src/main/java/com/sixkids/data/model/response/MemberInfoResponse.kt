package com.sixkids.data.model.response

import com.sixkids.model.MemberSimple
import com.sixkids.model.UserInfo

data class MemberInfoResponse(
    val id: Int,
    val name: String,
    val email: String,
    val photo: String,
    val role: String
)

data class MemberSimpleInfoResponse(
    val id: Long,
    val name: String,
    val photo: String?
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

internal fun MemberSimpleInfoResponse.toModel(): MemberSimple {
    return MemberSimple(
        id = id,
        name = name,
        photo = photo?: ""
    )
}