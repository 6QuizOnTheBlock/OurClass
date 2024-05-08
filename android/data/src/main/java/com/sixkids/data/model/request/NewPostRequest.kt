package com.sixkids.data.model.request

data class NewPostRequest(
    val title: String,
    val content: String,
    val secretStatus: Boolean,
    val postCategory: String
)