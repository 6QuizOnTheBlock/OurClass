package com.sixkids.model

import java.time.LocalDateTime

data class Report(
    val id: Int = 0,
    val group: Group = Group(),
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val file : String = "",
    val content: String = "",
    val accepted: Boolean = false,
)
