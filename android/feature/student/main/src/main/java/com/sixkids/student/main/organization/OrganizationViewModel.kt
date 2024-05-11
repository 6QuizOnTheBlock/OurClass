package com.sixkids.student.main.organization

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetOrganizationListUseCase
import com.sixkids.domain.usecase.organization.SaveSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.user.GetUserInfoUseCase
import com.sixkids.domain.usecase.user.UpdateFCMTokenUseCase
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class OrganizationViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getOrganizationListUseCase: GetOrganizationListUseCase,
    private val saveSelectedOrganizationIdUseCase: SaveSelectedOrganizationIdUseCase,
    private val updateFCMTokenUseCase: UpdateFCMTokenUseCase
) : BaseViewModel<OrganizationListState, OrganizationListEffect>(OrganizationListState()){

    fun initData() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }

            val userInfoJob = async { getUserInfoUseCase() }
            val organizationListJob = async { getOrganizationListUseCase() }

            val userInfoResult = userInfoJob.await()
                .onSuccess {
                    intent { copy(name = it.name, profilePhoto = it.photo) }
                }.onFailure {
                    postSideEffect(OrganizationListEffect.OnShowSnackBar(SnackbarToken(message = it.message ?: "알 수 없는 오류가 발생했습니다.")))
                }
            val organizationListResult = organizationListJob.await()
                .onSuccess {
                    intent { copy(organizationList = it) }
                }.onFailure {
                    postSideEffect(OrganizationListEffect.OnShowSnackBar(SnackbarToken(message = it.message ?: "알 수 없는 오류가 발생했습니다.")))
                }
            intent { copy(isLoading = false) }
        }
    }

    fun joinOrganizationClick(){
        postSideEffect(OrganizationListEffect.NavigateToJoinClass)
    }

    fun profileClick(){
        postSideEffect(OrganizationListEffect.NavigateToProfile)
    }

    fun organizationClick(id: Int){
        viewModelScope.launch {
            saveSelectedOrganizationIdUseCase(id)
        }
        postSideEffect(OrganizationListEffect.NavigateToHome)
    }

    fun onTokenRefresh(fcmToken: String) {
        viewModelScope.launch {
            updateFCMTokenUseCase(fcmToken).onFailure {
                Log.d(TAG, "onTokenRefresh: 토큰 갱신 실패 ${it.message}")
            }
        }
    }
}