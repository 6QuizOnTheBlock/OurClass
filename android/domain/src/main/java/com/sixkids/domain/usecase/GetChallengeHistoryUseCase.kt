package com.sixkids.domain.usecase

import androidx.paging.PagingData
import com.sixkids.domain.repository.ChallengeRepository
import com.sixkids.model.Challenge
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChallengeHistoryUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(
        organizationId: Int,
        memberId: Int? = null
    ): Flow<PagingData<Challenge>> =
        challengeRepository.getChallengeHistory(organizationId, memberId)
}
