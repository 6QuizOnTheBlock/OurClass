package com.sixkids.data.model.response

data class ReceiveRelayResponse(
    val senderName: String,
    val question: String,
    val lastStatus: Boolean,
    val demerit: Int,
)

internal fun ReceiveRelayResponse.toModel() = com.sixkids.model.RelayReceive(
    senderName = senderName,
    question = question,
    lastStatus = lastStatus,
    demerit = demerit,
)