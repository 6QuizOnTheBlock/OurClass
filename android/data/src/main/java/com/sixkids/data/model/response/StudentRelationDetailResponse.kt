package com.sixkids.data.model.response

import com.sixkids.model.StudentRelation

data class StudentRelationDetailResponse(
    val memberId: Long = -1,
    val memberName: String = "",
    val relationPoint: Int = 0,
    val tagGreetingCount: Int = 0,
    val groupCount: Int = 0,
    val receiveCount: Int = 0,
    val sendCount: Int = 0,
)

internal fun StudentRelationDetailResponse.toModel() = StudentRelation(
    id = memberId,
    name = memberName,
    relationPoint = relationPoint,
    tagGreetingCount = tagGreetingCount,
    groupCount = groupCount,
    receiveCount = receiveCount,
    sendCount = sendCount,
)