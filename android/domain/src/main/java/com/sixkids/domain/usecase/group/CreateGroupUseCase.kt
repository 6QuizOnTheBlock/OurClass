package com.sixkids.domain.usecase.group

import com.sixkids.domain.repository.GroupRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(key: String) = runCatching {
        groupRepository.createGroup(key)
    }
}
