package com.sixkids.feature.signin.login

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.user.GetRoleUseCase
import com.sixkids.domain.usecase.user.SignInUseCase
import com.sixkids.model.NotFoundException
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getRoleUseCase: GetRoleUseCase
) : BaseViewModel<LoginState, LoginEffect>(LoginState()){

    fun login(idToken: String){
        viewModelScope.launch {
            intent { copy(isLoading = true)}
            signInUseCase(idToken)
                .onSuccess {
                    getRoleUseCase()
                        .onSuccess {
                            postSideEffect(LoginEffect.NavigateToTeacherOrganizationList)
                        }.onFailure {
                            postSideEffect(LoginEffect.OnShowSnackBar(SnackbarToken("로그인 실패")))
                        }
                }.onFailure {
                    when(it){
                        is NotFoundException -> {
                            postSideEffect(LoginEffect.OnShowSnackBar(SnackbarToken(it.message)))
                            postSideEffect(LoginEffect.NavigateToSignUp)
                        }
                        else -> {
                            postSideEffect(LoginEffect.OnShowSnackBar(SnackbarToken(it.message?:"로그인 실패")))
                        }
                    }
                }
            intent { copy(isLoading = false)}
        }
    }
}