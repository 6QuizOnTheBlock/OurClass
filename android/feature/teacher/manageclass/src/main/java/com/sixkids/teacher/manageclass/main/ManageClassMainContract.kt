package com.sixkids.teacher.manageclass.main

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ManageClassMainState(
    val isLoading: Boolean = false,
    val classString: String = "",
): UiState

sealed interface ManageClassMainEffect: SideEffect{

}