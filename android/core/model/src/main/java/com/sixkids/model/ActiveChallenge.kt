package com.sixkids.model

import java.time.LocalDateTime

data class ActiveChallenge(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val totalUserCount: Int = 0,
    val activeUserCount: Int = 0,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val pendingChallengeCount: Int = 0,
)
