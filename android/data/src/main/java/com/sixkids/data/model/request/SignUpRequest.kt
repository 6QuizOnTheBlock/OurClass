package com.sixkids.data.model.request

import okhttp3.MultipartBody

data class SignUpRequest(
    val idToken: String,
    val file: MultipartBody.Part?,
    val defaultImage: Int,
    val role: String
)
