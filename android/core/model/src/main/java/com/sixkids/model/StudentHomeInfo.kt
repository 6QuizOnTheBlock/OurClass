package com.sixkids.model

data class StudentHomeInfo (
    val name: String,
    val photo: String,
    val className: String,
    val exp: Int,
    val notifyCount: Int,
    val relations: List<MemberSimpleWithScore>,
)