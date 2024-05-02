package com.sixkids.feature.signin.signup

import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.SavedStateHandle
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.sixkids.feature.signin.navigation.SignInRoute.SIGN_UP_TEACHER

private const val TAG = "D107"

@HiltViewModel
class SignUpPhotoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SignUpPhotoState, SignUpPhotoEffect>(SignUpPhotoState()) {

    val isTeacher = savedStateHandle.get<Boolean>(SIGN_UP_TEACHER)!!

    fun onProfilePhotoSelected(bitmap: Bitmap) {
        intent {
            copy(profileUserPhoto = bitmap)
            copy(profileDefaultPhoto = null)
        }
    }

    fun onProfileDefaultPhotoSelected(@DrawableRes photo: Int) {
        Log.d(TAG, "onProfileDefaultPhotoSelected: $photo")
        intent {
            copy(
                profileDefaultPhoto = photo,
                profileUserPhoto = null)
        }
    }

    fun signUp() {

    }

}