package com.sixkids.data.model.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)

internal fun TokenResponse.toModel() = com.sixkids.model.JwtToken(
    accessToken = accessToken,
    refreshToken = refreshToken
)