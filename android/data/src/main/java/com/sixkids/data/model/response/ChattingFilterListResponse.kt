package com.sixkids.data.model.response

data class ChattingFilterListResponse(
    val hasNext: Boolean,
    val words: List<ChattingFilterResponse>,
)

data class ChattingFilterResponse(
    val id: Long,
    val badWord: String,
)