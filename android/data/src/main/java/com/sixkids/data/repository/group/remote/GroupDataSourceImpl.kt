package com.sixkids.data.repository.group.remote

import com.sixkids.data.api.GroupService
import com.sixkids.data.model.response.GroupMatchingRoomResponse
import com.sixkids.data.model.response.GroupMatchingSuccessResponse
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

    override suspend fun joinGroup(key: String, joinStatus: Boolean) =
        groupService.joinGroup(key, joinStatus).getOrThrow().data

    override suspend fun createGroup(key: String): Long =
        groupService.createGroup(key).getOrThrow().data

    override suspend fun getMatchingGroup(
        organizationId: Long,
        minCount: Int,
        matchingType: String,
        members: List<Long>
    ): List<GroupMatchingSuccessResponse> =
        groupService.getMatchingGroup(organizationId, minCount, matchingType, members).getOrThrow().data
}
