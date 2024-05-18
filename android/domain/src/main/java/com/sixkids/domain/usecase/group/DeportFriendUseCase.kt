package com.sixkids.domain.usecase.group

import com.sixkids.domain.repository.GroupRepository
import javax.inject.Inject

class DeportFriendUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(key: String, memberId: Long) = runCatching {
        groupRepository.deportFriend(
            key, memberId
        )
    }

}
