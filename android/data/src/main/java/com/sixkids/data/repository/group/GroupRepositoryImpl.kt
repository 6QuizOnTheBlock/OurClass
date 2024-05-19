package com.sixkids.data.repository.group

import com.sixkids.data.model.response.toModel
import com.sixkids.data.repository.group.remote.GroupDataSource
import com.sixkids.domain.repository.GroupRepository
import com.sixkids.model.MatchingRoom
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupDataSource: GroupDataSource
) : GroupRepository {
    override suspend fun createMatchingRoom(challengeId: Long): MatchingRoom =
        groupDataSource.createMatchingRoom(challengeId).toModel()

    override suspend fun inviteFriend(key: String, memberId: Long) =
        groupDataSource.inviteFriend(key, memberId)

    override suspend fun deportFriend(key: String, memberId: Long) =
        groupDataSource.deportFriend(key, memberId)

    override suspend fun joinGroup(key: String, joinStatus: Boolean) =
        groupDataSource.joinGroup(key, joinStatus)

    override suspend fun createGroup(key: String): Long = groupDataSource.createGroup(key)

    override suspend fun getMatchingGroup(
        organizationId: Long,
        minCount: Int,
        matchingType: String,
        members: List<Long>
    ) = groupDataSource.getMatchingGroup(organizationId, minCount, matchingType, members).map { it.toModel() }
}
