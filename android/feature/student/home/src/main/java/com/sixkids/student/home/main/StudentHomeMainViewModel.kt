package com.sixkids.student.home.main

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentHomeMainViewModel @Inject constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase
) : BaseViewModel<StudentHomeMainState, StudentHomeMainEffect>(StudentHomeMainState()) {

    fun loadUserInfo() {
        viewModelScope.launch {
            loadUserInfoUseCase()
                .onSuccess { userinfo ->
                    intent {
                        copy(
                            studentName = userinfo.name,
                            studentImageUrl = userinfo.photo,
                        )
                    }
                }
                .onFailure {
                     postSideEffect(StudentHomeMainEffect.onShowSnackBar(it.message?:"회원정보 호출에 실패했습니다."))
                }
        }
    }
}