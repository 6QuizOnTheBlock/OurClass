package com.sixkids.teacher.main.organization

import com.sixkids.model.Organization
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface ClassListEffect: SideEffect{
    data object NavigateToNewClass : ClassListEffect
    data object NavigateToProfile : ClassListEffect
    data object NavigateToHome : ClassListEffect
    data class onShowSnackBar(val tkn : SnackbarToken) : ClassListEffect
}

data class ClassListState(
    val isLoading: Boolean = false,
    val name: String = "",
    val profilePhoto: String = "",
    val organizationList: List<Organization> = emptyList(),

) : UiState