package com.sixkids.data.repository.user.remote

import com.sixkids.data.model.response.StudentHomeResponse
import com.sixkids.model.JwtToken
import com.sixkids.model.MemberSimple
import com.sixkids.model.UserInfo
import java.io.File

interface UserRemoteDataSource {

    suspend fun testSignIn(email: String): JwtToken
    suspend fun signIn(idToken: String): JwtToken

    suspend fun signUp(file: File?, idToken: String, defaultImage: Int, role: String): JwtToken

    suspend fun getMemberInfo(): UserInfo

    suspend fun getMemberSimple(id: Long): MemberSimple

    suspend fun updateMemberProfilePhoto(file: File?, defaultImage: Int): String

    suspend fun autoSignIn(): JwtToken

    suspend fun updateFCMToken(fcmToken: String)

    suspend fun getStudentHomeInfo(organizationId: Long) : StudentHomeResponse
}
