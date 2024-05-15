package com.sixkids.data.repository.group.remote

import com.sixkids.data.api.GroupService
import com.sixkids.data.model.response.GroupMatchingRoomResponse
import javax.inject.Inject

class GroupDataSourceImpl @Inject constructor(
    private val groupService: GroupService
) : GroupDataSource {
    override suspend fun createMatchingRoom(challengeId: Long): GroupMatchingRoomResponse =
        groupService.createMatchingRoom(challengeId).getOrThrow().data

    override suspend fun inviteFriend(key: String, memberId: Long) =
        groupService.inviteFriend(key, memberId).getOrThrow().data

    override suspend fun deportFriend(key: String, memberId: Long) =
        groupService.deportFriend(key, memberId).getOrThrow().data
}
