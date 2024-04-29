package com.sixkids.feature.signin.login

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface LoginEffect : SideEffect{
    data object NavigateToHome : LoginEffect
    data object NavigateToSignUp : LoginEffect
    data class ShowSnackBar(val message: String) : LoginEffect
}

data class LoginState(
    val isLoading : Boolean = false,
) : UiState