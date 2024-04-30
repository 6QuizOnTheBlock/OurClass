package com.sixkids.data.repository.challenge

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sixkids.data.api.ChallengeService
import com.sixkids.data.repository.challenge.remote.ChallengeHistoryPagingSource
import com.sixkids.data.repository.challenge.remote.ChallengeHistoryPagingSource.Companion.DEFAULT_SIZE
import com.sixkids.domain.repository.ChallengeRepository
import com.sixkids.model.Challenge
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeService: ChallengeService,
) : ChallengeRepository {
    override suspend fun getChallengeHistory(
        organizationId: Int,
        memberId: Int?,
    ): Flow<PagingData<Challenge>> =
        Pager(
            config = PagingConfig(DEFAULT_SIZE),
            pagingSourceFactory = {
                ChallengeHistoryPagingSource(
                    challengeService,
                    organizationId,
                    memberId,
                )
            }
        ).flow

}
