package com.sixkids.data.repository.challenge.remote

import com.sixkids.data.api.ChallengeService
import com.sixkids.data.model.request.ChallengeCreateRequest
import com.sixkids.data.model.request.GroupRequest
import com.sixkids.data.model.response.toModel
import com.sixkids.model.AcceptStatus
import com.sixkids.model.ChallengeDetail
import com.sixkids.model.Challenge
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
        reward: Int,
        minCount: Int,
        groups: List<GroupSimple>,
    ): Int = challengeService.createChallenge(
        ChallengeCreateRequest(
            organizationId = organizationId,
            title = title,
            content = content,
            startTime = startTime,
            endTime = endTime,
            reward = reward,
            minCount = minCount,
            groups = groups.map {
                GroupRequest(
                    headCount = it.headCount,
                    leaderId = it.leaderId,
                    students = it.students
                )
            }
        )
    ).getOrThrow().data

    override suspend fun getChallengeSimple(challengeId: Int): Challenge
     = challengeService.getChallengeSimple(challengeId).getOrThrow().data.toModel()

    override suspend fun getChallengeDetail(challengeId: Long, groupId: Long?): ChallengeDetail =
        challengeService.getChallengeDetail(challengeId, groupId).getOrThrow().data.toModel()

    override suspend fun gradingChallenge(reportId: Long, acceptStatus: AcceptStatus) =
        challengeService.gradingChallenge(reportId, acceptStatus.name).getOrThrow().data
}
