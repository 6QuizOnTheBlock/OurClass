package com.sixkids.model

data class MemberDetail(
    val name: String = "",
    val photo: String = "",
    val isolationPoint: Double = 0.0,
    val isolationRank: Int = -1,
    val exp: Int = -1,
    val challengeCount: Int = -1,
    val relayCount: Int = -1,
    val postCount: Int = -1,
)
