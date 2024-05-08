package com.sixkids.data.model.response

import com.sixkids.model.Challenge
import java.time.LocalDateTime

data class ChallengeSimpleResponse(
    val id: Int,
    val title: String,
    val content: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val reward: Int,
)

fun ChallengeSimpleResponse.toModel(): Challenge {
    return Challenge(
        id = id,
        title = title,
        content = content,
        startTime = startTime,
        endTime = endTime,
        reword = reward
    )
}
