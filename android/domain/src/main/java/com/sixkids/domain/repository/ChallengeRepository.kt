package com.sixkids.domain.repository

import androidx.paging.PagingData
import com.sixkids.model.AcceptStatus
import com.sixkids.model.Challenge
import com.sixkids.model.ChallengeDetail
import com.sixkids.model.GroupSimple
import com.sixkids.model.RunningChallenge
import com.sixkids.model.RunningChallengeByStudent
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ChallengeRepository {
    suspend fun getChallengeHistory(
        organizationId: Int,
        memberId: Int?
    ): Flow<PagingData<Challenge>>

    suspend fun getRunningChallenge(organizationId: Int): RunningChallenge

    suspend fun getRunningChallengesByStudent(organizationId: Int): RunningChallengeByStudent

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

    suspend fun getChallengeSimple(
        challengeId: Int
    ): Challenge

    suspend fun getChallengeDetail(challengeId: Long, groupId: Long?): ChallengeDetail

    suspend fun gradingChallenge(reportId: Long, acceptStatus: AcceptStatus)
}
