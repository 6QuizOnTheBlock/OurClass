package com.sixkids.domain.repository

interface TokenRepository {
    suspend fun getAccessToken(): String
    suspend fun saveAccessToken(token: String)
    suspend fun getRefreshToken(): String
    suspend fun saveRefreshToken(token: String)
    suspend fun saveIdToken(token: String)
    suspend fun getIdToken(): String
    suspend fun deleteIdToken()
    suspend fun clearTokens()
}