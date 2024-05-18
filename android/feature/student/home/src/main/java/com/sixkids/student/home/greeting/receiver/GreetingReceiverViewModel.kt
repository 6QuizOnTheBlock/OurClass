package com.sixkids.student.home.greeting.receiver

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.organization.GreetingUseCase
import com.sixkids.model.GreetingNFC
import com.sixkids.model.NotFoundException
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreetingReceiverViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val greetingUseCase: GreetingUseCase
): BaseViewModel<GreetingReceiverState, GreetingReceiverEffect>(GreetingReceiverState()){

    fun initData(){
        viewModelScope.launch {
            getSelectedOrganizationIdUseCase()
                .onSuccess {
                    intent { copy(organizationId = it) }
                }
                .onFailure {
                    intent { copy(organizationId = -1) }
                }
        }
    }

    fun onNfcReceived(greetingNfc: GreetingNFC){
        if (greetingNfc.organizationId ==  uiState.value.organizationId){
            viewModelScope.launch {
                greetingUseCase(uiState.value.organizationId.toLong(), greetingNfc.senderId.toLong())
                    .onSuccess {
                        if (it > 0){
                            postSideEffect(GreetingReceiverEffect.OnShowSnackBar(SnackbarToken("친구와 인사를 주고 받았어요!")))
                            postSideEffect(GreetingReceiverEffect.NavigateToHome)
                        }else{
                            postSideEffect(GreetingReceiverEffect.OnShowSnackBar(SnackbarToken("인사에 실패했어요")))
                        }
                    }
                    .onFailure {
                        when(it){
                            is NotFoundException -> postSideEffect(GreetingReceiverEffect.OnShowSnackBar(SnackbarToken("같은 반 친구가 아닙니다!")))
                            else -> postSideEffect(GreetingReceiverEffect.OnShowSnackBar(SnackbarToken("인사에 실패했어요")))
                        }
                    }
            }
        }else{
            postSideEffect(GreetingReceiverEffect.OnShowSnackBar(SnackbarToken("같은 반 친구가 아닙니다!")))
        }
    }
}