package com.sixkids.model

import java.time.LocalDateTime

data class Challenge(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val headCount: Int = 0,
    val reword: Int = 0,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
)
