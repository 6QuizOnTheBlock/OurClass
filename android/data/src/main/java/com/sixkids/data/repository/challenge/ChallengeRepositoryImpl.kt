package com.sixkids.data.repository.challenge

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sixkids.data.api.ChallengeService
import com.sixkids.data.model.response.toModel
import com.sixkids.data.repository.challenge.remote.ChallengeHistoryPagingSource
import com.sixkids.data.repository.challenge.remote.ChallengeHistoryPagingSource.Companion.DEFAULT_SIZE
import com.sixkids.data.repository.challenge.remote.ChallengeRemoteDataSourceImpl
import com.sixkids.domain.repository.ChallengeRepository
import com.sixkids.model.AcceptStatus
import com.sixkids.model.Challenge
import com.sixkids.model.ChallengeDetail
import com.sixkids.model.GroupSimple
import com.sixkids.model.RunningChallengeByStudent
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

    override suspend fun getRunningChallengesByStudent(
        organizationId: Int
    ): RunningChallengeByStudent {
        val a = challengeRemoteDataSourceImpl.getRunningChallengesByStudent(organizationId)
        Log.d("ttt", "getRunningChallengesByStudent: $a")
        Log.d("ttt", "getRunningChallengesByStudent: ${a.toModel()}")
        return a.toModel()
    }

    override suspend fun createChallenge(
        organizationId: Int,
        title: String,
        content: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        reward: Int,
        minCount: Int,
        groups: List<GroupSimple>
    ): Long = challengeRemoteDataSourceImpl.createChallenge(
        organizationId,
        title,
        content,
        startTime,
        endTime,
        reward,
        minCount,
        groups
    )

    override suspend fun getChallengeSimple(challengeId: Int): Challenge =
        challengeRemoteDataSourceImpl.getChallengeSimple(challengeId)

    override suspend fun getChallengeDetail(challengeId: Long, groupId: Long?): ChallengeDetail =
        challengeRemoteDataSourceImpl.getChallengeDetail(challengeId, groupId)

    override suspend fun gradingChallenge(reportId: Long, acceptStatus: AcceptStatus) =
        challengeRemoteDataSourceImpl.gradingChallenge(reportId, acceptStatus)
}
