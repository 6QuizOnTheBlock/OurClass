package com.sixkids.student.relay.pass.tagging.receiver

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.relay.ReceiveRelayUseCase
import com.sixkids.student.relay.navigation.RelayRoute
import com.sixkids.student.relay.pass.tagging.RelayNfc
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class RelayTaggingReceiverViewModel @Inject constructor(
    private val receiveRelayUseCase: ReceiveRelayUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel<RelayTaggingReceiverState, RelayTaggingReceiverEffect>(RelayTaggingReceiverState()){
    private val receivedRelayId = savedStateHandle.get<Long>(RelayRoute.RELAY_ID_NAME)

    fun init() {
        intent { copy(relayId = receivedRelayId?:-1L) }
    }

    fun onNfcReceived(relayNfc: RelayNfc) {
        if (relayNfc.relayId == receivedRelayId){
            viewModelScope.launch {
                receiveRelayUseCase(relayNfc.relayId.toInt(), relayNfc.senderId, relayNfc.question)
                    .onSuccess {
                        intent { copy(relayReceive = it) }
                    }
                    .onFailure {
                        Log.e(TAG, "Failed to receive relay", it)
                    }
            }
        }
    }

}