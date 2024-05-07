package com.sixkids.data.repository.user.remote

import com.sixkids.model.JwtToken
import com.sixkids.model.UserInfo
import java.io.File

interface UserRemoteDataSource {
    suspend fun signIn(idToken: String): JwtToken

    suspend fun signUp(file: File?, idToken: String, defaultImage: Int, role: String): JwtToken

    suspend fun getMemberInfo(): UserInfo

    suspend fun updateMemberProfilePhoto(file: File?, defaultImage: Int): String

    suspend fun autoSignIn(): JwtToken

    suspend fun updateFCMToken(fcmToken: String)
}
