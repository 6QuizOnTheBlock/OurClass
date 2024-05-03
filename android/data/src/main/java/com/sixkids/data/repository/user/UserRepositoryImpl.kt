package com.sixkids.data.repository.user

import com.sixkids.data.repository.user.remote.UserDataSource
import com.sixkids.domain.repository.TokenRepository
import com.sixkids.domain.repository.UserRepository
import com.sixkids.model.JwtToken
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val tokenRepository: TokenRepository
) : UserRepository{
    override suspend fun signIn(idToken: String): JwtToken {
        try {
            val response = userDataSource.signIn(idToken)

            tokenRepository.saveAccessToken(response.accessToken)
            tokenRepository.saveRefreshToken(response.refreshToken)
            tokenRepository.saveIdToken(idToken)

            return response
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun signUp(
        file: File?,
        defaultImage: Int,
        role: String
    ): JwtToken {
        try {
            val idToken = tokenRepository.getIdToken()
            val response = userDataSource.signUp(file, idToken, defaultImage, role)

            tokenRepository.saveAccessToken(response.accessToken)
            tokenRepository.saveRefreshToken(response.refreshToken)

            tokenRepository.deleteIdToken()

            return response
        }catch (e: Exception){
            throw e
        }
    }
}