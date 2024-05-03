package com.sixkids.data.repository.user.remote

import com.sixkids.model.JwtToken
import java.io.File

interface UserDataSource {
    suspend fun signIn(idToken: String): JwtToken

    suspend fun signUp(file: File?, idToken: String, defaultImage: Int, role: String): JwtToken
}