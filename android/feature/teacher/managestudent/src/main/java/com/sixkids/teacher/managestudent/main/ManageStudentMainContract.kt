package com.sixkids.teacher.managestudent.main

import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ManageStudentMainState(
    val isLoading: Boolean = false,
    val classString: String = "",
    val organizationId: Int = 0,
    val studentList: List<MemberSimple> = emptyList(),
): UiState

sealed interface ManageStudentMainEffect : SideEffect{
    data class NavigateToStudentDetail(val relayId: Long) : ManageStudentMainEffect

    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ManageStudentMainEffect
}