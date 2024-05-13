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
}
