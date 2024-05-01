package com.sixkids.domain.repository

import com.sixkids.model.JwtToken

interface UserRepository {
    suspend fun signIn(idToken: String): JwtToken
}