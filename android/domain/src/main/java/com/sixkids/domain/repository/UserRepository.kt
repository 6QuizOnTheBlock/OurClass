package com.sixkids.domain.repository

import com.sixkids.model.JwtToken
import java.io.File

interface UserRepository {
    suspend fun signIn(idToken: String): JwtToken

    suspend fun signUp(file: File?, defaultImage: Int, role: String): JwtToken

    suspend fun getRole(): String
}