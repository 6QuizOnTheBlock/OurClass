package com.sixkids.domain.usecase.user

import com.sixkids.domain.repository.UserRepository
import javax.inject.Inject

class LoadUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke() = runCatching {
        userRepository.loadUserInfo()
    }
}