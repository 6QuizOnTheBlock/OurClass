package com.sixkids.data.repository.user

import android.util.Log
import com.sixkids.data.repository.user.remote.UserDataSource
import com.sixkids.domain.repository.TokenRepository
import com.sixkids.domain.repository.UserRepository
import com.sixkids.model.JwtToken
import javax.inject.Inject

private const val TAG = "D107"
class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val tokenRepository: TokenRepository
) : UserRepository{
    override suspend fun signIn(idToken: String): JwtToken {
        try {
            val response = userDataSource.signIn(idToken)

            tokenRepository.saveAccessToken(response.accessToken)
            tokenRepository.saveRefreshToken(response.refreshToken)

            return response
        }catch (e: Exception){
            throw e
        }
    }
}