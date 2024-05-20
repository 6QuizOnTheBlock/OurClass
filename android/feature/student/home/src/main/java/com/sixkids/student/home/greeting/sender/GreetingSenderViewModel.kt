package com.sixkids.student.home.greeting.sender

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.GreetingNFC
import com.sixkids.model.UserInfo
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class GreetingSenderViewModel @Inject constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase
): BaseViewModel<GreetingSenderState, GreetingSenderEffect>(GreetingSenderState()){
    private var organizationId: Int = -1
    private lateinit var userInfo: UserInfo

    fun initData(){
        viewModelScope.launch {
            loadUserInfoUseCase().onSuccess {user ->
                userInfo = user

                getSelectedOrganizationIdUseCase()
                    .onSuccess {
                        organizationId = it
                        intent { copy(greetingNfc = GreetingNFC(user.id, it)) }
                    }
            }.onFailure {
                Log.d(TAG, "initData: $it")
            }
        }
    }

}