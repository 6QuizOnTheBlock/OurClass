package com.sixkids.student.relay.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.relay.GetRelayDetailUseCase
import com.sixkids.student.relay.navigation.RelayRoute.RELAY_ID_NAME
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RelayDetailViewModel @Inject constructor(
    private val getRelayDetailUseCase: GetRelayDetailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RelayDetailState, RelayDetailSideEffect>(RelayDetailState())
{
    private val relayId = savedStateHandle.get<Long>(RELAY_ID_NAME)

    fun getRelayDetail() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getRelayDetailUseCase(relayId!!)
                .onSuccess { relayDetail ->
                    intent { copy(relayDetail = relayDetail) }
                }
                .onFailure { exception ->
                    postSideEffect(RelayDetailSideEffect.HandleException(exception, ::getRelayDetail))
                }
            intent { copy(isLoading = false) }
        }
    }
}