package com.sixkids.domain.usecase.group

import com.sixkids.domain.repository.GroupRepository
import javax.inject.Inject

class CreateGroupMatchingRoomUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(challengeId: Long) = runCatching {
        groupRepository.createMatchingRoom(challengeId)
    }
}
