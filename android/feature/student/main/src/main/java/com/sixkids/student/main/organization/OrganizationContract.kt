package com.sixkids.student.main.organization

import com.sixkids.model.Organization
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState


sealed interface OrganizationListEffect : SideEffect {
    data object NavigateToJoinClass : OrganizationListEffect
    data object NavigateToProfile : OrganizationListEffect
    data object NavigateToHome : OrganizationListEffect
    data class OnShowSnackBar(val tkn: SnackbarToken) : OrganizationListEffect
}

data class OrganizationListState(
    val isLoading: Boolean = false,
    val name: String = "",
    val profilePhoto: String = "",
    val organizationList: List<Organization> = emptyList(),
) : UiState