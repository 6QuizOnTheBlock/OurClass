package com.sixkids.teacher.manageclass.setting

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface ClassSettingEffect : SideEffect {
    data object navigateBack : ClassSettingEffect
    data class onShowSnackBar(val message: String) : ClassSettingEffect
}


data class ClassSettingState(
    val isLoading: Boolean = false,
    val classString: String = "",
    val schoolName: String = "",
    val grade: Int? = null,
    val classNumber: Int? = null,
): UiState