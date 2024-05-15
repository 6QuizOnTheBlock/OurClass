package com.sixkids.data.repository.group.remote

import com.sixkids.data.model.response.GroupMatchingRoomResponse

interface GroupDataSource {
    suspend fun createMatchingRoom(challengeId: Long): GroupMatchingRoomResponse

    suspend fun inviteFriend(key: String, memberId: Long)

    suspend fun deportFriend(key: String, memberId: Long)

    suspend fun joinGroup(key: String, joinStatus: Boolean)
}
