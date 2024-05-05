package com.sixkids.data.repository.user.local

interface UserLocalDataSource {
    suspend fun getRole() : String
    suspend fun saveRole(role: String)

    suspend fun getUserId() : Int
    suspend fun saveUserId(userId: Int)

    suspend fun getUserName() : String
    suspend fun saveUserName(userName: String)

    suspend fun getUserProfileImage() : String
    suspend fun saveUserProfileImage(image: String)
}