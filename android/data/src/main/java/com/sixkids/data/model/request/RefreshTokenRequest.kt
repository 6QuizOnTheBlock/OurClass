package com.sixkids.data.model.request

data class RefreshTokenRequest(
    val accessToken: String,
    val refreshToken: String

)
