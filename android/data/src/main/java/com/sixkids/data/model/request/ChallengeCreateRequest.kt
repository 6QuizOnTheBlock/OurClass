package com.sixkids.data.model.request

import java.time.LocalDateTime

data class ChallengeCreateRequest(
    val organizationId: Int,
    val title: String,
    val content: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val minCount: Int,
    val reward: Int,
    val groups: List<GroupRequest>
)

data class GroupRequest(
    val headCount: Int,
    val leaderId: Long,
    val students: List<Long>
)
