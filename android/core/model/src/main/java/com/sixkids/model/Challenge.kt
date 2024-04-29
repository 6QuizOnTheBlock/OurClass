package com.sixkids.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

data class Challenge(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val userCount: Int = 0,
    val startDate: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val endDate: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
)