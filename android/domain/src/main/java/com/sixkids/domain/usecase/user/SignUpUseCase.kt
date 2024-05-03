package com.sixkids.domain.usecase.user

import com.sixkids.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(file: File?, defaultImage: Int, role: String) = runCatching {
        userRepository.signUp(file, defaultImage, role)
    }
}
