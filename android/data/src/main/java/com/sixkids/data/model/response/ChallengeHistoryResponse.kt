package com.sixkids.data.model.response

import com.sixkids.model.Challenge
import java.time.LocalDateTime

data class ChallengeHistoryResponse(
    val page: Int,
    val size: Int,
    val last: Boolean,
    val challenges: List<ChallengeResponse>
)

data class ChallengeResponse(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val headCount: Int = 0,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
)

internal fun ChallengeResponse.toModel() = Challenge(
    id = id,
    title = title,
    content = content,
    headCount = headCount,
    startTime = startTime,
    endTime = endTime
)
