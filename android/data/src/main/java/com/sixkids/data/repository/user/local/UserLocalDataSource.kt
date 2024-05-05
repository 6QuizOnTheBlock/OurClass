package com.sixkids.data.repository.user.local

interface UserLocalDataSource {
    suspend fun getRole() : String
    suspend fun saveRole(role: String)
}