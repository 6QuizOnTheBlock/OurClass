package com.sixkids.data.model.response

import com.sixkids.model.JwtToken

data class SignUpResponse (
    val accessToken: String,
    val refreshToken: String,
    val role: String
)

internal fun SignUpResponse.toModel() = JwtToken(
    accessToken = accessToken,
    refreshToken = refreshToken
)
