package com.sixkids.data.repository.user

import com.sixkids.data.model.response.toModel
import com.sixkids.data.repository.user.local.UserLocalDataSource
import com.sixkids.data.repository.user.remote.UserRemoteDataSource
import com.sixkids.domain.repository.TokenRepository
import com.sixkids.domain.repository.UserRepository
import com.sixkids.model.JwtToken
import com.sixkids.model.MemberSimple
import com.sixkids.model.StudentHomeInfo
import com.sixkids.model.UserInfo
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val tokenRepository: TokenRepository
) : UserRepository {
    override suspend fun signIn(idToken: String): JwtToken {
        try {
            val response = userRemoteDataSource.signIn(idToken)

            tokenRepository.saveAccessToken(response.accessToken)
            tokenRepository.saveRefreshToken(response.refreshToken)

            return response
        } catch (e: Exception) {
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
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getRole(): String {
        return userLocalDataSource.getRole()
    }

    override suspend fun getMemberInfo(): UserInfo {
        return userRemoteDataSource.getMemberInfo()
    }

    override suspend fun getMemberSimpleInfo(id: Long): MemberSimple {
        return userRemoteDataSource.getMemberSimple(id)
    }

    override suspend fun updateMemberProfilePhoto(file: File?, defaultImage: Int): String {
        return userRemoteDataSource.updateMemberProfilePhoto(file, defaultImage)
    }

    override suspend fun signOut(): Boolean {
        return userLocalDataSource.signOut()
    }

    override suspend fun updateFCMToken(fcmToken: String) =
        userRemoteDataSource.updateFCMToken(fcmToken)

    override suspend fun autoSignIn(): JwtToken {
        try {
            val response = userRemoteDataSource.autoSignIn()

            tokenRepository.saveAccessToken(response.accessToken)
            tokenRepository.saveRefreshToken(response.refreshToken)

            return response
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun loadUserInfo(): UserInfo {
        return userLocalDataSource.getUserInfo()
    }

    override suspend fun getStudentHomeInfo(organizationId: Long): StudentHomeInfo {
        return userRemoteDataSource.getStudentHomeInfo(organizationId).toModel()
    }
}
