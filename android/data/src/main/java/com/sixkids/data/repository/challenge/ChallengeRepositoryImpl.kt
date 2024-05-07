package com.sixkids.data.repository.challenge

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sixkids.data.api.ChallengeService
import com.sixkids.data.repository.challenge.remote.ChallengeHistoryPagingSource
import com.sixkids.data.repository.challenge.remote.ChallengeHistoryPagingSource.Companion.DEFAULT_SIZE
import com.sixkids.data.repository.challenge.remote.ChallengeRemoteDataSourceImpl
import com.sixkids.domain.repository.ChallengeRepository
import com.sixkids.model.Challenge
import com.sixkids.model.GroupSimple
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeService: ChallengeService,
    private val challengeRemoteDataSourceImpl: ChallengeRemoteDataSourceImpl,
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

    override suspend fun getRunningChallenge(organizationId: Int) =
        challengeRemoteDataSourceImpl.getRunningChallenges(organizationId)

    override suspend fun createChallenge(
        organizationId: Int,
        title: String,
        content: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        reword: Int,
        minCount: Int,
        groups: List<GroupSimple>
    ): Int = challengeRemoteDataSourceImpl.createChallenge(
        organizationId,
        title,
        content,
        startTime,
        endTime,
        reword,
        minCount,
        groups
    )

}
