package com.sixkids.student.main.joinorganization

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface JoinOrganizationEffect : SideEffect {
    data object NavigateToOrganizationList : JoinOrganizationEffect
    data class OnShowSnackBar(val tkn: SnackbarToken) : JoinOrganizationEffect
}

data class JoinOrganizationState(
    val isLoading: Boolean = false,
    val code: String = "",
    val id: String = "",
) : UiState