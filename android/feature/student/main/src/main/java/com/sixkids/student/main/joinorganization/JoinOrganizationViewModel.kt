package com.sixkids.student.main.joinorganization

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.JoinOrganizationUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinOrganizationViewModel @Inject constructor(
    private val joinOrganizationUseCase: JoinOrganizationUseCase
): BaseViewModel<JoinOrganizationState, JoinOrganizationEffect>(JoinOrganizationState()){

    fun updateCode(code: String){
        intent { copy(code = code) }
    }

    fun updateId(id: String){
        intent { copy(id = id) }
    }

    fun joinOrganizationClick(){
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            joinOrganizationUseCase(uiState.value.id.toInt(), uiState.value.code)
                .onSuccess {
                    if (it>0){
                        postSideEffect(JoinOrganizationEffect.NavigateToOrganizationList)
                    }
                }.onFailure {

                }
        }
    }
}