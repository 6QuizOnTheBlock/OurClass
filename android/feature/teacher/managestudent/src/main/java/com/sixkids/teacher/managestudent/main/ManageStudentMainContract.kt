package com.sixkids.teacher.managestudent.main

import com.sixkids.model.MemberSimple

data class ManageStudentMainState(
    val isLoading: Boolean = false,
    val classString: String = "",
    val studentList: List<MemberSimple> = emptyList(),
)