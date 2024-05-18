package com.sixkids.model

import java.time.LocalDateTime

data class RunningChallengeByStudent(
    val challenge: Challenge,
    val leaderStatus: Boolean? = null,
    val memberNames: List<MemberSimple>,
    val type: GroupType,
    val createTime: LocalDateTime?,
    val endStatus: Boolean?
)
