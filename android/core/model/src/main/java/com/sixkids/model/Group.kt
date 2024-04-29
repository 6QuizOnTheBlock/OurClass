package com.sixkids.model

data class Group(
    val id: Int = 0,
    val leaderId: Int = 0,
    val studentList : List<MemberSimple> = emptyList(),
)
