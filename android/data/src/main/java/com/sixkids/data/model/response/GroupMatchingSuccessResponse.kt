package com.sixkids.data.model.response

import com.sixkids.model.ChallengeGroup

data class GroupMatchingSuccessResponse(
    val members: List<MemberSimpleResponse>,
    val headCount: Int
)

internal fun GroupMatchingSuccessResponse.toModel(): ChallengeGroup =
    ChallengeGroup(
        headCount = headCount,
        memberList = members.map { it.toModel() }
    )
