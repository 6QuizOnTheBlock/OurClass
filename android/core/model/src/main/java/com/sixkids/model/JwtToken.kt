package com.sixkids.model


data class JwtToken(
    val accessToken : String,
    val refreshToken : String
)
