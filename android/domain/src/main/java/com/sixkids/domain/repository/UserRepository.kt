package com.sixkids.domain.repository

import com.sixkids.model.JwtToken
import com.sixkids.model.UserInfo
import java.io.File

interface UserRepository {
    suspend fun signIn(idToken: String): JwtToken

    suspend fun signUp(file: File?, defaultImage: Int, role: String): JwtToken

    suspend fun getRole(): String

    suspend fun getMemberInfo(): UserInfo

    suspend fun updateMemberProfilePhoto(file: File?, defaultImage: Int): String

    suspend fun signOut() : Boolean

    suspend fun updateFCMToken(fcmToken: String)

    suspend fun autoSignIn(): JwtToken
}
