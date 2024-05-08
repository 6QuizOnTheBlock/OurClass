package com.sixkids.model

import java.time.LocalDateTime

data class RunningChallenge(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val totalMemberCount: Int = 0,
    val doneMemberCount: Int = 0,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val waitingCount: Int = 0,
)
