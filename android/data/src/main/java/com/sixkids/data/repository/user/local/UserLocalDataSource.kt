package com.sixkids.data.repository.user.local

import com.sixkids.model.UserInfo

interface UserLocalDataSource {
    suspend fun getRole() : String
    suspend fun saveRole(role: String)

    suspend fun getUserId() : Int
    suspend fun saveUserId(userId: Int)

    suspend fun getUserName() : String
    suspend fun saveUserName(userName: String)

    suspend fun getUserProfileImage() : String
    suspend fun saveUserProfileImage(image: String)

    suspend fun getUserInfo() : UserInfo
    suspend fun saveUserInfo(id: Int, name: String, email: String, photo: String, role: String)
}