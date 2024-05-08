package com.sixkids.data.repository.challenge.remote

import com.sixkids.model.AcceptStatus
import com.sixkids.model.ChallengeDetail
import com.sixkids.model.GroupSimple
import com.sixkids.model.RunningChallenge
import java.time.LocalDateTime

interface ChallengeRemoteDataSource {

    suspend fun getRunningChallenges(organizationId: Int): RunningChallenge
    suspend fun createChallenge(
        organizationId: Int,
        title: String,
        content: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        reword: Int,
        minCount: Int,
        groups: List<GroupSimple>
    ): Long

    suspend fun getChallengeDetail(challengeId: Long, groupId: Long?): ChallengeDetail

    suspend fun gradingChallenge(reportId: Long, acceptStatus: AcceptStatus)
}
