package com.sixkids.teacher.manageclass.invite

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface ClassInviteEffect: SideEffect {
    data class onShowSnackBar(val message: String): ClassInviteEffect
}

data class ClassInviteState(
    val isLoading: Boolean = false,
    val classString: String = "",
    val classInviteCode: String? = null,
    val classId: Int? = null
): UiState