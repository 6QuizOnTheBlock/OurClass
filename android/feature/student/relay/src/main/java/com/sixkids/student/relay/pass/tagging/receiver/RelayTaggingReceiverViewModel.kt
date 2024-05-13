package com.sixkids.student.relay.pass.tagging.receiver

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.sixkids.student.relay.navigation.RelayRoute
import com.sixkids.student.relay.pass.tagging.RelayNfc
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class RelayTaggingReceiverViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<RelayTaggingReceiverState, RelayTaggingReceiverEffect>(RelayTaggingReceiverState()){
    private val receivedRelayId = savedStateHandle.get<Long>(RelayRoute.RELAY_ID_NAME)

    fun init() {
        intent { copy(relayId = receivedRelayId?:-1L) }
    }

    fun onNfcReceived(relayNfc: RelayNfc) {
        if (relayNfc.relayId == receivedRelayId){

        }
    }

}