package com.sixkids.teacher.main.organization

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetOrganizationListUseCase
import com.sixkids.domain.usecase.user.GetUserInfoUseCase
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrganizationViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getOrganizationListUseCase: GetOrganizationListUseCase
) : BaseViewModel<ClassListState, ClassListEffect>(ClassListState()) {

    fun initData() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }

            val userInfoJob = async { getUserInfoUseCase() }
            val organizationListJob = async { getOrganizationListUseCase() }

            val userInfoResult = userInfoJob.await()
                .onSuccess {
                    intent { copy(name = it.name, profilePhoto = it.photo) }
                }.onFailure {
                    postSideEffect(ClassListEffect.OnShowSnackBar(SnackbarToken(message = it.message ?: "알 수 없는 오류가 발생했습니다.")))
                }
            val organizationListResult = organizationListJob.await()
                .onSuccess {
                    intent { copy(organizationList = it) }
                }.onFailure {
                    postSideEffect(ClassListEffect.OnShowSnackBar(SnackbarToken(message = it.message ?: "알 수 없는 오류가 발생했습니다.")))
                }
            intent { copy(isLoading = false) }
        }
    }

    fun newOrganizationClick(){
        postSideEffect(ClassListEffect.NavigateToNewClass)
    }

    fun profileClick(){
        postSideEffect(ClassListEffect.NavigateToProfile)
    }

    fun organizationClick(id: Int){
        postSideEffect(ClassListEffect.NavigateToHome)
    }
}

