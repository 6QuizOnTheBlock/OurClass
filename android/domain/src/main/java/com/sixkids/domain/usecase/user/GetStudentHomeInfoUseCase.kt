package com.sixkids.domain.usecase.user

import com.sixkids.domain.repository.UserRepository
import javax.inject.Inject

class GetStudentHomeInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(organizationId: Long) =
        userRepository.getStudentHomeInfo(organizationId)
}