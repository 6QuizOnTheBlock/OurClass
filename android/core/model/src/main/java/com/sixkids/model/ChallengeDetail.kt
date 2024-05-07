package com.sixkids.model

import java.time.LocalDateTime

data class ChallengeDetail(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val headCount: Int = 0,
    val teamCount: Int = 0,
    val reportList: List<Report> = emptyList(),
)
