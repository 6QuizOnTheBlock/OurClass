package com.sixkids.model

import java.time.LocalDateTime

data class RunningRelay(
    val id: Long = 0,
    val totalMemberCount: Int = 0, // 전체 턴 횟수 (선생님만 볼 수 있음)
    val doneMemberCount: Int = 0, // 현재 진행된 턴 횟수 (선생님만 볼 수 있음)
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val curMemberNickname: String = "",
    val myTurnStatus : Boolean = false
)
