package com.sixkids.data.model.response

import com.sixkids.model.RunningChallenge

data class RunningChallengeResponse(
    val challenge: ChallengeResponse,
    val waitingCount: Int,
    val doneMemberCount: Int
)

internal fun RunningChallengeResponse.toModel(): RunningChallenge {
    val challenge = challenge.toModel()
    return RunningChallenge(
        id = challenge.id,
        title = challenge.title,
        content = challenge.content,
        totalMemberCount = challenge.headCount,
        doneMemberCount = doneMemberCount,
        startTime = challenge.startTime,
        endTime = challenge.endTime,
        waitingCount = waitingCount
    )
}
