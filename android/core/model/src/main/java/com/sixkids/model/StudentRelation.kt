package com.sixkids.model

data class StudentRelation(
    val id: Long = -1,
    val name: String = "",
    val relationPoint: Int = 0,
    val tagGreetingCount: Int = 0,
    val groupCount: Int = 0,
    val receiveCount: Int = 0,
    val sendCount: Int = 0,
)
