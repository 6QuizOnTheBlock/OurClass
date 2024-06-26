package com.sixkids.data.model.response

import com.sixkids.model.ClassSummary
import com.sixkids.model.MemberSimpleClassSummary

data class ClassSummaryResponse(
    val challengeCounts: List<ClassSummaryMemberResponse>,
    val relayCounts: List<ClassSummaryMemberResponse>,
    val postCounts: List<ClassSummaryMemberResponse>,
)

data class ClassSummaryMemberResponse(
    val member: MemberSimpleInfoResponse,
    val count: Int
)

fun ClassSummaryMemberResponse.toModel(): MemberSimpleClassSummary {
    return MemberSimpleClassSummary(
        member.toModel(),
        count
    )
}

internal fun ClassSummaryResponse.toModel(): ClassSummary {
    return ClassSummary(
        challengeCounts.map { it.toModel() },
        relayCounts.map { it.toModel() },
        postCounts.map { it.toModel() }
    )
}