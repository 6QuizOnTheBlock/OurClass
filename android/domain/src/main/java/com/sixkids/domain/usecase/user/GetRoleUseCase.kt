package com.sixkids.domain.usecase.user

import com.sixkids.domain.repository.UserRepository
import javax.inject.Inject

class GetRoleUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getRole()
}