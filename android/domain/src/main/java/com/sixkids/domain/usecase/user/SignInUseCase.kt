package com.sixkids.domain.usecase.user

import com.sixkids.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(idToken: String) = runCatching {
        userRepository.signIn(idToken)
    }

    suspend fun testSignIn(email: String) = userRepository.testSignIn(email)
}
