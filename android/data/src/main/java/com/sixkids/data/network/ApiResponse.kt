package com.sixkids.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    val message: String,
    val status: String,
    val data: T,
)
