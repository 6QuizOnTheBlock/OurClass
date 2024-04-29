package com.sixkids.model

import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class Challenge(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val userCount: Int = 0,
    val startDate: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val endDate: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
)
