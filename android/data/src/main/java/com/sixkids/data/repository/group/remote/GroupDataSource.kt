package com.sixkids.data.repository.group.remote

import com.sixkids.data.model.response.GroupMatchingRoomResponse
import com.sixkids.data.model.response.GroupMatchingSuccessResponse

interface GroupDataSource {
    suspend fun createMatchingRoom(challengeId: Long): GroupMatchingRoomResponse

    suspend fun inviteFriend(key: String, memberId: Long)

    suspend fun deportFriend(key: String, memberId: Long)

    suspend fun joinGroup(key: String, joinStatus: Boolean)

    suspend fun createGroup(key: String): Long

    suspend fun getMatchingGroup(
        organizationId: Long,
        minCount: Int,
        matchingType: String,
        members: List<Long>
    ): List<GroupMatchingSuccessResponse>
}
