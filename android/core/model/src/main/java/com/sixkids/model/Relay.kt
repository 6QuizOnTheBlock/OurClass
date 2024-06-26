package com.sixkids.model

import java.time.LocalDateTime

data class Relay(
    val id: Long = 0,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val lastTurn: Int = 0,
    val lastMemberName: String = "",
    val totalCount: Int = 0
)
