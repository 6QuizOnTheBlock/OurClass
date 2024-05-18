package com.sixkids.data.model.response

import com.sixkids.model.MemberDetail

data class StudentDetailResponse(
    val name: String = "",
    val photo: String = "",
    //todo 등수로 바꾸기
    val isolationPoint: Double = 0.0,
    val exp: Int = -1,
    val challengeCount: Int = -1,
    val relayCount: Int = -1,
    val postCount: Int = -1,
)

internal fun StudentDetailResponse.toModel(): MemberDetail {
    return MemberDetail(
        name = this.name,
        photo = this.photo,
        isolationPoint = this.isolationPoint,
        exp = this.exp,
        challengeCount = this.challengeCount,
        relayCount = this.relayCount,
        postCount = this.postCount,
    )
}
