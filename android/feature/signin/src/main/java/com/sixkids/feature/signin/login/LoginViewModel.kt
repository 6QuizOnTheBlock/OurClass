package com.sixkids.feature.signin.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.user.AutoSignInUseCase
import com.sixkids.domain.usecase.user.GetRoleUseCase
import com.sixkids.domain.usecase.user.SignInUseCase
import com.sixkids.model.NotFoundException
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val autoSignInUseCase: AutoSignInUseCase,
    private val signInUseCase: SignInUseCase,
    private val getRoleUseCase: GetRoleUseCase
) : BaseViewModel<LoginState, LoginEffect>(LoginState()){

    fun autoSignIn(){
        viewModelScope.launch {
            autoSignInUseCase()
                .onSuccess {
                    getRoleUseCase()
                        .onSuccess {
                            when(it){
                                "TEACHER" -> postSideEffect(LoginEffect.NavigateToTeacherOrganizationList)
                                "STUDENT" -> {
                                    Log.d(TAG, "autoSignIn: STUDENT")
                                    postSideEffect(LoginEffect.NavigateToStudentOrganizationList)}
                            }
                        }.onFailure {
                            Log.d(TAG, "autoSignIn: ${it.message}")
                        }
                }.onFailure {
                    Log.d(TAG, "autoSignIn: ${it.message}")
                }
        }
    }

    fun login(idToken: String){
        viewModelScope.launch {
            intent { copy(isLoading = true)}
            signInUseCase(idToken)
                .onSuccess {
                    getRoleUseCase()
                        .onSuccess {
                            when(it){
                                "TEACHER" -> postSideEffect(LoginEffect.NavigateToTeacherOrganizationList)
                                "STUDENT" -> {postSideEffect(LoginEffect.NavigateToStudentOrganizationList)}
                            }
                        }.onFailure {
                            postSideEffect(LoginEffect.OnShowSnackBar(SnackbarToken("로그인에 실패했습니다")))
                        }
                }.onFailure {
                    when(it){
                        is NotFoundException -> {
                            postSideEffect(LoginEffect.OnShowSnackBar(SnackbarToken("회원가입을 진행 해주세요")))
                            postSideEffect(LoginEffect.NavigateToSignUp)
                        }
                        else -> {
                            postSideEffect(LoginEffect.OnShowSnackBar(SnackbarToken(it.message?:"로그인에 실패했습니다")))
                        }
                    }
                }
            intent { copy(isLoading = false)}
        }
    }
}
