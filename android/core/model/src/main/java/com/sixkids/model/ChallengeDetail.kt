package com.sixkids.model

import java.time.LocalDateTime

data class ChallengeDetail(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val userCount: Int = 0,
    val teamCount: Int = 0,
    val reportList: List<Report> = emptyList(),
)
