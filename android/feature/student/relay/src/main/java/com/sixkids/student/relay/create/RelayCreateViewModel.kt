package com.sixkids.student.relay.create

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.relay.CreateRelayUseCase
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class RelayCreateViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val createRelayUseCase: CreateRelayUseCase
) : BaseViewModel<RelayCreateState, RelayCreateEffect>(RelayCreateState()) {

    fun init() {
        viewModelScope.launch {
            getSelectedOrganizationIdUseCase()
                .onSuccess {
                    intent { copy(orgId = it) }
                }.onFailure {
                    postSideEffect(RelayCreateEffect.OnShowSnackBar(SnackbarToken("학급 정보를 불러오는데 실패했습니다")))
                }
        }
    }

    fun newRelayClick() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            if (uiState.value.orgId == -1){
                init()
            }else{
                Log.d(TAG, "newRelayClick: ${uiState.value.question} ${uiState.value.orgId}")
                createRelayUseCase(uiState.value.orgId, uiState.value.question)
                    .onSuccess {
                        postSideEffect(RelayCreateEffect.NavigateToRelayResult(it))
                    }.onFailure {
                        postSideEffect(RelayCreateEffect.OnShowSnackBar(SnackbarToken("이어 달리기 생성에 실패했습니다")))
                    }
            }
        }
    }

    fun updateQuestion(question: String) {
        intent { copy(question = question) }
    }
}