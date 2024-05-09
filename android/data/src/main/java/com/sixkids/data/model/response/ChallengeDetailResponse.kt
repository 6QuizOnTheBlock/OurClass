package com.sixkids.data.model.response

import com.sixkids.model.ChallengeDetail
import com.squareup.moshi.Json

data class ChallengeDetailResponse(
    @Json(name = "challengeSimpleDTO")
    val  challenge: ChallengeResponse,
    val reports: List<ReportResponse>
)

internal fun ChallengeDetailResponse.toModel() = ChallengeDetail(
    id = challenge.id,
    title = challenge.title,
    content = challenge.content,
    startTime = challenge.startTime,
    endTime = challenge.endTime,
    headCount = challenge.headCount,
    teamCount = reports.size,
    reportList = reports.map { it.toModel() }
)
