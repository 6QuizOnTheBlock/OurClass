package com.sixkids.model

import java.time.LocalDateTime

data class Challenge(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val userCount: Int = 0,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
)
