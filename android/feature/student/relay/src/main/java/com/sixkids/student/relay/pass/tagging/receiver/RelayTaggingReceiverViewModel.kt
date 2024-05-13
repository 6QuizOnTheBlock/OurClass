package com.sixkids.student.relay.pass.tagging.receiver

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.sixkids.student.relay.navigation.RelayRoute
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class RelayTaggingReceiverViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<RelayTaggingReceiverState, RelayTaggingReceiverEffect>(RelayTaggingReceiverState()){
    private val relayId = savedStateHandle.get<Long>(RelayRoute.RELAY_ID_NAME)

    fun init() {
        Log.d(TAG, "init: $relayId")
    }

}