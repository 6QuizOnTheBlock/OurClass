package com.sixkids.teacher.main.classlist

import com.sixkids.model.Class
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface ClassListEffect: SideEffect{
    data object NavigateToNewClass : ClassListEffect
    data object NavigateToProfile : ClassListEffect
    data object NavigateToSignIn : ClassListEffect
}

data class ClassListState(
    val isLoading: Boolean = false,
    val name: String = "",
    val profilePhoto: String = "",
    val classList: List<Class> = emptyList()
) : UiState