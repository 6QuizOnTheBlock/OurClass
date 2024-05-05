package com.sixkids.data.repository.user

import com.sixkids.data.repository.user.local.UserLocalDataSource
import com.sixkids.data.repository.user.remote.UserRemoteDataSource
import com.sixkids.domain.repository.TokenRepository
import com.sixkids.domain.repository.UserRepository
import com.sixkids.model.JwtToken
import com.sixkids.model.UserInfo
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val tokenRepository: TokenRepository
) : UserRepository{
    override suspend fun signIn(idToken: String): JwtToken {
        try {
            val response = userRemoteDataSource.signIn(idToken)

            tokenRepository.saveAccessToken(response.accessToken)
            tokenRepository.saveRefreshToken(response.refreshToken)

            return response
        }catch (e: Exception){
            tokenRepository.saveIdToken(idToken)
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
            val response = userRemoteDataSource.signUp(file, idToken, defaultImage, role)

            tokenRepository.saveAccessToken(response.accessToken)
            tokenRepository.saveRefreshToken(response.refreshToken)

            tokenRepository.deleteIdToken()

            return response
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getRole(): String {
        return userLocalDataSource.getRole()
    }

    override suspend fun getMemberInfo(): UserInfo {
        return userRemoteDataSource.getMemberInfo()
    }
}