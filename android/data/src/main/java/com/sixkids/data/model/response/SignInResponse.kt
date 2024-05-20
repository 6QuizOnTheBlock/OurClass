package com.sixkids.data.model.response

import com.sixkids.model.JwtToken

data class SignInResponse(
    val accessToken : String,
    val refreshToken : String,
    val role : String
)

internal fun SignInResponse.toModel() = JwtToken(
    accessToken = accessToken,
    refreshToken = refreshToken
)
