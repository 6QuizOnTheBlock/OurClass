package com.sixkids.student.main.joinorganization

import com.sixkids.model.Organization
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface JoinOrganizationEffect : SideEffect {
    data object NavigateToJoinClass : JoinOrganizationEffect
    data object NavigateToProfile : JoinOrganizationEffect
    data object NavigateToHome : JoinOrganizationEffect
    data class OnShowSnackBar(val tkn: SnackbarToken) : JoinOrganizationEffect
}

data class JoinOrganizationState(
    val isLoading: Boolean = false,
    val name: String = "",
    val organizationName: String = "",
    val studentName: String = ""
) : UiState