package com.sixkids.model

import java.time.LocalDateTime

data class Report(
    val id: Long = 0,
    val group: Group = Group(),
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val file : String = "",
    val content: String = "",
    val acceptStatus: AcceptStatus = AcceptStatus.BEFORE,
)
