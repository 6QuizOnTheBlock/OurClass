package com.sixkids.teacher.main.profile

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.user.GetUserInfoUseCase
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel<ProfileState, ProfileEffect>(ProfileState()) {

    fun initData() {
        viewModelScope.launch {
            getUserInfoUseCase()
                .onSuccess {
                    intent {
                        copy(
                            name = it.name,
                            originalProfilePhoto = it.photo
                        )
                    }
                }.onFailure {
                    postSideEffect(
                        ProfileEffect.OnShowSnackBar(
                            SnackbarToken(
                                it.message ?: "알 수 없는 오류가 발생했습니다."
                            )
                        )
                    )
                }

        }
    }

    fun onProfilePhotoSelected(bitmap: Bitmap) {
        intent {
            copy(
                changedProfileUserPhoto = bitmap,
                changedProfileDefaultPhoto = null,
                gender = null
            )
        }
    }

    fun onProfileDefaultPhotoSelected(@DrawableRes photo: Int, gender: Gender) {
        intent {
            copy(
                changedProfileDefaultPhoto = photo,
                changedProfileUserPhoto = null,
                gender = gender
            )
        }
    }

    fun onChangeDoneClick(){

    }
}