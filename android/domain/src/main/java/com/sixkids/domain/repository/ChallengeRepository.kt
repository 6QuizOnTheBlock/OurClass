package com.sixkids.domain.repository

import androidx.paging.PagingData
import com.sixkids.model.Challenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getChallengeHistory(
        organizationId: Int,
        memberId: Int?
    ): Flow<PagingData<Challenge>>
}
