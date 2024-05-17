package com.sixkids.data.model.response

import com.sixkids.model.RelaySend

data class SendRelayResponse(
    val prevMemberName: String,
    val prevQuestion: String
)

internal fun SendRelayResponse.toModel() = RelaySend(
    prevMemberName = prevMemberName,
    prevQuestion = prevQuestion
)