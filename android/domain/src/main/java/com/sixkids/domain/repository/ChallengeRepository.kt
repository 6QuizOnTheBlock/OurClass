package com.sixkids.domain.repository

import androidx.paging.PagingData
import com.sixkids.model.Challenge
import com.sixkids.model.GroupSimple
import com.sixkids.model.RunningChallenge
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ChallengeRepository {
    suspend fun getChallengeHistory(
        organizationId: Int,
        memberId: Int?
    ): Flow<PagingData<Challenge>>

    suspend fun getRunningChallenge(organizationId: Int): RunningChallenge

    suspend fun createChallenge(
        organizationId: Int,
        title: String,
        content: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        reword: Int,
        minCount: Int,
        groups: List<GroupSimple>
    ): Int
}
