package com.sixkids.feature.signin.signup

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.user.GetRoleUseCase
import com.sixkids.domain.usecase.user.SignUpUseCase
import com.sixkids.feature.signin.navigation.SignInRoute.SIGN_UP_TEACHER
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class SignUpPhotoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val signUpUseCase: SignUpUseCase,
    private val getRoleUseCase: GetRoleUseCase
) : BaseViewModel<SignUpPhotoState, SignUpPhotoEffect>(SignUpPhotoState()) {

    val isTeacher = savedStateHandle.get<Boolean>(SIGN_UP_TEACHER)!!

    fun onProfilePhotoSelected(bitmap: Bitmap) {
        intent {
            copy(
                profileUserPhoto = bitmap,
                profileDefaultPhoto = null,
                gender = null
            )
        }
    }

    fun onProfileDefaultPhotoSelected(@DrawableRes photo: Int, gender: Gender) {
        intent {
            copy(
                profileDefaultPhoto = photo,
                profileUserPhoto = null,
                gender = gender
            )
        }
    }

    fun signUp(file: File?) {
        viewModelScope.launch {
            intent { copy(isLoading = true)}
            val defaultImage = when (file) {
                null -> {
                    when (uiState.value.gender) {
                        null -> 0
                        Gender.MAN -> if (isTeacher) 1 else 3
                        Gender.WOMAN -> if (isTeacher) 2 else 4
                    }
                }
                else -> 0
            }

            signUpUseCase(
                file = file,
                defaultImage = defaultImage,
                role = if (isTeacher) "TEACHER" else "STUDENT"
            ).onSuccess {
                postSideEffect(SignUpPhotoEffect.OnShowSnackBar(SnackbarToken(
                    message = "환영합니다."
                )))
                getRoleUseCase()
                    .onSuccess {
                        when(it){
                            "TEACHER" -> postSideEffect(SignUpPhotoEffect.NavigateToTeacherOrganizationList)
                            "STUDENT" -> postSideEffect(SignUpPhotoEffect.NavigateToStudentOrganizationList)
                        }
                    }.onFailure {

                    }
            }.onFailure {
                postSideEffect(SignUpPhotoEffect.OnShowSnackBar(SnackbarToken(
                    message = it.message ?: "알 수 없는 에러 입니다."
                )))
            }
            intent { copy(isLoading = false)}
        }
    }
}
