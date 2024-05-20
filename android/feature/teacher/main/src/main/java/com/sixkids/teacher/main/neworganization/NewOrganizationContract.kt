package com.sixkids.teacher.main.neworganization

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface NewOrganizationEffect : SideEffect{
    data object NavigateToOrganizationList : NewOrganizationEffect
    data class OnShowSnackBar(val tkn : SnackbarToken) : NewOrganizationEffect
}

data class NewOrganizationState(
    val isLoading: Boolean = false,
    val name: String = "",
    val grade: String = "",
    val classNo: String = "",
) : UiState