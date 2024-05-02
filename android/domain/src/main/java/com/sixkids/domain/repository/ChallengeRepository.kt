package com.sixkids.domain.repository

import androidx.paging.PagingData
import com.sixkids.model.Challenge
import com.sixkids.model.RunningChallenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getChallengeHistory(
        organizationId: Int,
        memberId: Int?
    ): Flow<PagingData<Challenge>>

    suspend fun getRunningChallenge(organizationId: Int): RunningChallenge
}
