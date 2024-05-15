package com.sixkids.domain.repository

import com.sixkids.model.MatchingRoom

interface GroupRepository {
    suspend fun createMatchingRoom(challengeId: Long): MatchingRoom

    suspend fun inviteFriend(key: String, memberId: Long)

    suspend fun deportFriend(key: String, memberId: Long)
}
