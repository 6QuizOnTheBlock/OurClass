package com.sixkids.student.main.profile

import android.graphics.Bitmap
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface ProfileEffect : SideEffect {
    data object NavigateToSignIn : ProfileEffect
    data object NavigateToOrganizationList : ProfileEffect
    data class OnShowSnackBar(val tkn: SnackbarToken) : ProfileEffect
}

data class ProfileState(
    val isLoading: Boolean = false,
    val name: String = "",
    val gender: Gender? = null,
    val originalProfilePhoto: String? = null,
    val changedProfileDefaultPhoto: Int? = null,
    val changedProfileUserPhoto: Bitmap? = null
) : UiState

enum class Gender{
    MAN,
    WOMAN
}