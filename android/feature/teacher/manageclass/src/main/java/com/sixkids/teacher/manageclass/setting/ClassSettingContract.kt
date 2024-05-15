package com.sixkids.teacher.manageclass.setting

import com.sixkids.ui.base.SideEffect

sealed interface ClassSettingEffect : SideEffect {

}


data class ClassSettingState(
    val isLoading: Boolean = false,
    val classString: String = "",
)