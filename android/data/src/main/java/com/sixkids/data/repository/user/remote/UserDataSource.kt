package com.sixkids.data.repository.user.remote

import com.sixkids.model.JwtToken

interface UserDataSource {
    suspend fun signIn(idToken: String): JwtToken
}