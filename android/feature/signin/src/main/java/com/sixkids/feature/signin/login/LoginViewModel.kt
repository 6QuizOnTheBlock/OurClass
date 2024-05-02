package com.sixkids.feature.signin.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.user.SignInUseCase
import com.sixkids.model.NotFoundException
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HONG"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : BaseViewModel<LoginState, LoginEffect>(LoginState()){

    fun login(idToken: String){
        viewModelScope.launch {
            signInUseCase(idToken)
                .onSuccess {
                    postSideEffect(LoginEffect.NavigateToHome)
                }.onFailure {
                    when(it){
                        is NotFoundException -> {
                            Log.d(TAG, "login: NotFoundException")
                            postSideEffect(LoginEffect.NavigateToSignUp)
                        }
                        else -> {
                            Log.d(TAG, "login: ${it.message}")
                        }
                    }
                }
        }
    }
}