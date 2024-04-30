package com.sixkids.data.repository.user.remote

import com.sixkids.data.api.SignInService
import com.sixkids.data.model.request.SignInRequest
import com.sixkids.data.model.response.toModel
import com.sixkids.model.JwtToken
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val signInService: SignInService
) : UserDataSource{
    override suspend fun signIn(email: String): JwtToken {
        val response = signInService.signIn(
            SignInRequest(email)
        )
        return response.getOrThrow().toModel()
    }
}
