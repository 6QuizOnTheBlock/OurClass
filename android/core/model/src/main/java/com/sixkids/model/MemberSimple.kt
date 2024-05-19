package com.sixkids.model

import kotlinx.serialization.Serializable

@Serializable
data class MemberSimple(
    val id: Long = 0L,
    val name: String = "",
    val photo: String = "",
)
