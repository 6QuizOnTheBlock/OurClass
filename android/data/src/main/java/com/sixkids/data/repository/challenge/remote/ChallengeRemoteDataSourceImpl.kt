package com.sixkids.data.repository.challenge.remote

import com.sixkids.data.api.ChallengeService
import com.sixkids.data.model.request.ChallengeCreateRequest
import com.sixkids.data.model.request.GroupRequest
import com.sixkids.data.model.response.toModel
import com.sixkids.model.GroupSimple
import java.time.LocalDateTime
import javax.inject.Inject

class ChallengeRemoteDataSourceImpl @Inject constructor(
    private val challengeService: ChallengeService,
) : ChallengeRemoteDataSource {
    override suspend fun getRunningChallenges(organizationId: Int) =
        challengeService.getRunningChallenge(organizationId).getOrThrow().data.toModel()

    override suspend fun createChallenge(
        organizationId: Int,
        title: String,
        content: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        reword: Int,
        minCount: Int,
        groups: List<GroupSimple>?,
    ) = challengeService.createChallenge(
        ChallengeCreateRequest(
            organizationId = organizationId,
            title = title,
            content = content,
            startTime = startTime,
            endTime = endTime,
            reword = reword,
            minCount = minCount,
            groups = groups?.map {
                GroupRequest(
                    headCount = it.headCount,
                    leaderId = it.leaderId,
                    students = it.students
                )
            }
        )
    ).getOrThrow().data
}
