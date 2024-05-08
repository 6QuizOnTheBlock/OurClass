package com.sixkids.data.model.response

data class ApiResponse<T>(
    val message: String,
    val status: String,
    val data: T,
)