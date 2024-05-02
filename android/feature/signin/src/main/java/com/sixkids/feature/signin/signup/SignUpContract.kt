package com.sixkids.feature.signin.signup

import android.graphics.Bitmap
import androidx.compose.ui.graphics.painter.Painter
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface SignUpRoleEffect : SideEffect {
    data class NavigateToSignUpPhoto(val isTeacher: Boolean) : SignUpRoleEffect
}

data class SignUpRoleState(
    val role : Role = Role.TEACHER,
) : UiState

enum class Role{
    TEACHER,
    STUDENT
}


sealed interface SignUpPhotoEffect : SideEffect {
    data class NavigateToHome(val role: Role) : SignUpPhotoEffect
}

data class SignUpPhotoState(
    val profileDefaultPhoto: Int? = null,
    val profileUserPhoto: Bitmap? = null
) : UiState