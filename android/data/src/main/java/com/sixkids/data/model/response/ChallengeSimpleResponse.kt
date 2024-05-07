package com.sixkids.data.model.response

import com.sixkids.model.Challenge
import java.time.LocalDateTime

data class ChallengeSimpleResponse(
    val id: Int,
    val title: String,
    val content: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val reward: Int,
)

fun ChallengeSimpleResponse.toModel(): Challenge {
    return Challenge(
        id = id,
        title = title,
        content = content,
        startTime = startDate,
        endTime = endDate,
        reword = reward
    )
}
