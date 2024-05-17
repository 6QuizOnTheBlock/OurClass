package com.sixkids.data.model.response

import com.sixkids.model.GroupType
import com.sixkids.model.RunningChallengeByStudent
import com.squareup.moshi.Json
import java.time.LocalDateTime

data class RunningChallengeByStudentResponse(
    @Json(name = "challengeSimpleDTO")
    val challenge: ChallengeResponse,
    val leaderStatus: Boolean?,
    @Json(name = "memberList")
    val memberNames: List<MemberSimpleResponse>?,
    val type: String,
    val createTime: LocalDateTime?,
    val endStatus: Boolean?
)

internal fun RunningChallengeByStudentResponse.toModel() =
    RunningChallengeByStudent(
        challenge = challenge.toModel(),
        leaderStatus = leaderStatus,
        memberNames = memberNames?.map { it.toModel() } ?: emptyList(),
        type = GroupType.valueOf(type),
        createTime = createTime,
        endStatus = endStatus
    )
