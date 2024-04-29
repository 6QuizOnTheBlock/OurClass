package com.sixkids.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

data class ActiveChallenge(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val totalUserCount: Int = 0,
    val activeUserCount: Int = 0,
    val startDate: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val endDate: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val pendingChallengeCount: Int = 0,
)
