package com.sixkids.domain.repository

import com.sixkids.model.ChallengeGroup
import com.sixkids.model.MatchingRoom

interface GroupRepository {
    suspend fun createMatchingRoom(challengeId: Long): MatchingRoom

    suspend fun inviteFriend(key: String, memberId: Long)

    suspend fun deportFriend(key: String, memberId: Long)

    suspend fun joinGroup(key: String, joinStatus: Boolean)

    suspend fun createGroup(key: String): Long

    suspend fun getMatchingGroup(
        organizationId: Long,
        minCount: Int,
        matchingType: String,
        members: List<Long>
    ): List<ChallengeGroup>
}
