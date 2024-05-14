package com.sixkids.student.relay.pass.tagging

import kotlinx.serialization.Serializable

@Serializable
data class RelayNfc(
    val relayId: Long,
    val senderId: Long,
    val question: String
)
