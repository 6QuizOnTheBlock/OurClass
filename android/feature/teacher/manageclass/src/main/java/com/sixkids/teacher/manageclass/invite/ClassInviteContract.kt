package com.sixkids.teacher.manageclass.invite

import com.sixkids.ui.base.SideEffect

sealed interface ClassInviteEffect: SideEffect {

}

data class ClassInviteState(
    val isLoading: Boolean = false,
    val classString: String = "",
    val classInviteCode: String? = null,
)