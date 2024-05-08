package com.sixkids.data.model.response

import com.sixkids.model.Group

data class GroupResponse(
    val id: Long,
    val headCount: Int,
    val leaderId: Long,
    val students: List<StudentResponse>,
)

internal fun GroupResponse.toModel() = Group(
    id = id,
    leaderId = leaderId,
    studentList = students.map { it.toModel() }
)
