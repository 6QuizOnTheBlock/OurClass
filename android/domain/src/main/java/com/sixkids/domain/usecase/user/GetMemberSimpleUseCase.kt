package com.sixkids.domain.usecase.user

import com.sixkids.domain.repository.UserRepository
import javax.inject.Inject

class GetMemberSimpleUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(id: Long) = runCatching {
        userRepository.getMemberSimpleInfo(id)
    }
}
