package com.sixkids.data.repository.user

import com.sixkids.data.api.SignInService
import com.sixkids.data.model.request.SignInRequest
import com.sixkids.data.repository.user.remote.UserDataSource
import com.sixkids.domain.repository.UserRepository
import com.sixkids.model.JwtToken
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository{
    override suspend fun signIn(idToken: String): JwtToken {
        return userDataSource.signIn(idToken)
    }

}