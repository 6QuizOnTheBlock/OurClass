package com.sixkids.feature.signin.login

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface LoginEffect : SideEffect{
    data object NavigateToHome : LoginEffect
    data object NavigateToSignUp : LoginEffect
    data object NavigateToTeacherOrganizationList : LoginEffect

    //학생 홈으로 이동
    data object NavigateToStudentHome : LoginEffect
    data class OnShowSnackBar(val tkn : SnackbarToken) : LoginEffect
}

data class LoginState(
    val isLoading : Boolean = false,
) : UiState
