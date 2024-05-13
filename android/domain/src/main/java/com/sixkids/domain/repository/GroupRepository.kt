package com.sixkids.domain.repository

import com.sixkids.model.MatchingRoom

interface GroupRepository {
    suspend fun createMatchingRoom(challengeId: Long): MatchingRoom
}
