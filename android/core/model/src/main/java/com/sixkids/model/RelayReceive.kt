package com.sixkids.model

data class RelayReceive(
    val senderName: String = "",
    val question: String = "",
    val lastStatus: Boolean = false,
    val demerit: Int = 0,
)
