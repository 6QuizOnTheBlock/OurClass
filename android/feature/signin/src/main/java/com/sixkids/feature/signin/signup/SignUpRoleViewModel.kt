package com.sixkids.feature.signin.signup

import android.graphics.Bitmap
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpRoleViewModel @Inject constructor(

) : BaseViewModel<SignUpRoleState, SignUpRoleEffect>(SignUpRoleState()){

    fun onTeacherClick(isTeacher: Boolean){
        postSideEffect(SignUpRoleEffect.NavigateToSignUpPhoto(isTeacher))
    }
}