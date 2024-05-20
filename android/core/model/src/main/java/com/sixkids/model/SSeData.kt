package com.sixkids.model

import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import kotlinx.datetime.LocalDateTime as KotlinLocalDateTime

@Serializable
data class SseData(
    val eventType: String,
    val receiverId: Long,
    val url: Long?,
    val data: String?,
    val time: KotlinLocalDateTime = LocalDateTime.now().toKotlinLocalDateTime()

)
