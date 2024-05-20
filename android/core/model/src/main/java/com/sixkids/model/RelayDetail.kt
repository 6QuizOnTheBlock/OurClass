package com.sixkids.model

import java.time.LocalDateTime

data class RelayDetail(
    val id: Long = 0,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val lastTurn: Int = 0,
    val lastMemberName: String = "",
    val runnerList: List<RelayQuestion> = emptyList(),

)
