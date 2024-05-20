package com.sixkids.model

import kotlinx.serialization.Serializable

@Serializable
data class GreetingNFC(
    val senderId: Int = -1,
    val organizationId: Int = -1
)
