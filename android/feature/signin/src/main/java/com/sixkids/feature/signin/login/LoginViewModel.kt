package com.sixkids.feature.signin.login

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : BaseViewModel<LoginState, LoginEffect>(LoginState()){

    fun login(email: String, token: String){

    }
}