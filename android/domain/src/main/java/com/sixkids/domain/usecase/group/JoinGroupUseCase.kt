package com.sixkids.domain.usecase.group

import com.sixkids.domain.repository.GroupRepository
import javax.inject.Inject

class JoinGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(key: String, joinStatus: Boolean) = runCatching {
        groupRepository.joinGroup(key, joinStatus)
    }
}
