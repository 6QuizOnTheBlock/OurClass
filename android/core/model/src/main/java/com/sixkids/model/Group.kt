package com.sixkids.model

data class Group(
    val id: Long = 0,
    val leaderId: Long = 0,
    val studentList : List<MemberSimple> = emptyList(),
)
