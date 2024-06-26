package com.sixkids.data.repository.challenge.remote

import com.sixkids.data.model.response.RunningChallengeByStudentResponse
import com.sixkids.model.AcceptStatus
import com.sixkids.model.Challenge
import com.sixkids.model.ChallengeDetail
import com.sixkids.model.GroupSimple
import com.sixkids.model.RunningChallenge
import java.time.LocalDateTime

interface ChallengeRemoteDataSource {

    suspend fun getRunningChallenges(organizationId: Int): RunningChallenge

    suspend fun getRunningChallengesByStudent(organizationId: Int): RunningChallengeByStudentResponse

    suspend fun createChallenge(
        organizationId: Int,
        title: String,
        content: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        reward: Int,
        minCount: Int,
        groups: List<GroupSimple>
    ): Long

    suspend fun getChallengeDetail(challengeId: Long, groupId: Long?): ChallengeDetail

    suspend fun gradingChallenge(reportId: Long, acceptStatus: AcceptStatus)

    suspend fun getChallengeSimple(
        challengeId: Int
    ): Challenge

    suspend fun connectSse()
}
