package com.sixkids.domain.usecase.user

import com.sixkids.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(email: String) = runCatching {
        userRepository.signIn(email)
    }
}