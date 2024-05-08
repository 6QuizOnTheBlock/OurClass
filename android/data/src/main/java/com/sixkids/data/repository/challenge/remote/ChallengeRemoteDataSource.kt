package com.sixkids.data.repository.challenge.remote

import com.sixkids.model.Challenge
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
        reward: Int,
        minCount: Int,
        groups: List<GroupSimple>
    ): Int

    suspend fun getChallengeSimple(
        challengeId: Int
    ): Challenge
}
