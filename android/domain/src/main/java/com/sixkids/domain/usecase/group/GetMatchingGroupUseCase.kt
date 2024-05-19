package com.sixkids.domain.usecase.group

import com.sixkids.domain.repository.GroupRepository
import javax.inject.Inject

class GetMatchingGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        organizationId: Long,
        minCount: Int,
        matchingType: String,
        members: List<Long>
    ) = runCatching {
        groupRepository.getMatchingGroup(organizationId, minCount, matchingType, members)
    }
}
