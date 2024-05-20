package com.sixkids.data.model.response

import com.sixkids.model.ChatFilterWord

data class ChattingFilterListResponse(
    val hasNext: Boolean,
    val words: List<ChattingFilterResponse>,
)

data class ChattingFilterResponse(
    val id: Long,
    val badWord: String,
)

fun ChattingFilterResponse.toModel(): ChatFilterWord {
    return ChatFilterWord(
        id = id,
        badWord = badWord,
    )
}