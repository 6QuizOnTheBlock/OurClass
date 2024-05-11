package com.sixkids.student.main.profile

import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.user.GetUserInfoUseCase
import com.sixkids.domain.usecase.user.SignOutUseCase
import com.sixkids.domain.usecase.user.UpdateUserProfilePhotoUseCase
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

private const val TAG = "D107"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserProfilePhotoUseCase: UpdateUserProfilePhotoUseCase,
    private val signOutUseCase: SignOutUseCase
): BaseViewModel<ProfileState, ProfileEffect>(ProfileState()){
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

    fun onChangeDoneClick(newProfilePhoto: File?) {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            var defaultImage = 0
            if (newProfilePhoto == null && uiState.value.changedProfileDefaultPhoto == null) {
                // 변경사항 없음 뒤로가기
                postSideEffect(ProfileEffect.NavigateToOrganizationList)
            } else {
                defaultImage = when (newProfilePhoto) {
                    null -> {
                        when (uiState.value.gender) {
                            null -> 0
                            Gender.MAN -> 3
                            Gender.WOMAN -> 4
                        }
                    }
                    else -> 0
                }
            }

            updateUserProfilePhotoUseCase(newProfilePhoto, defaultImage)
                .onSuccess {
                    postSideEffect(ProfileEffect.NavigateToOrganizationList)
                }.onFailure {
                    Log.d(TAG, "onChangeDoneClick: ${it.message}")
                    postSideEffect(
                        ProfileEffect.OnShowSnackBar(
                            SnackbarToken(
                                it.message ?: "알 수 없는 오류가 발생했습니다."
                            )
                        )
                    )
                }
            intent { copy(isLoading = false) }
        }
    }

    fun onSignOutClick() {
        viewModelScope.launch {
            if(signOutUseCase()){
                postSideEffect(ProfileEffect.NavigateToSignIn)
            }else{
                ProfileEffect.OnShowSnackBar(
                    SnackbarToken("로그아웃에 실패했습니다. 다시 시도해주세요.")
                )
            }
        }
    }
}