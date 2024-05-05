package com.sixkids.data.model.request

import java.time.LocalDateTime

data class ChallengeCreateRequest(
    val organizationId: Int,
    val title: String,
    val content: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val minCount: Int,
    val reword: Int,
    val groups: List<GroupRequest>? = null,
)

data class GroupRequest(
    val headCount: Int,
    val leaderId: Int,
    val students: List<Long>
)
