package com.sixkids.model

import java.time.LocalDateTime

data class RelayQuestion(
    val id: Long = 0,
    val memberId: Long = 0,
    val memberName: String = "",
    val memberPhoto: String = "",
    val time: LocalDateTime = LocalDateTime.now(),
    val question: String = "",
    val turn: Int = 0,
    val endStatus: Boolean = false,
)
