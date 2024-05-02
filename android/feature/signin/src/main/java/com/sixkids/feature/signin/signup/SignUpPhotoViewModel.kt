package com.sixkids.feature.signin.signup

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpPhotoViewModel @Inject constructor(
) : BaseViewModel<SignUpPhotoState, SignUpPhotoEffect>(SignUpPhotoState()){

    fun onProfilePhotoSelected(bitmap: Bitmap){
        intent {
            copy(profileUserPhoto = bitmap)
            copy(profileDefaultPhoto = null)
        }
    }

    fun onProfileDefaultPhotoSelected(@DrawableRes photo: Int){
        intent {
            copy(profileDefaultPhoto = photo)
            copy(profileUserPhoto = null)
        }
    }

    fun signUp(){

    }

}