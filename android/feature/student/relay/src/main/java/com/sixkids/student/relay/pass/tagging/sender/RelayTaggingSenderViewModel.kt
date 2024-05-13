package com.sixkids.student.relay.pass.tagging.sender

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.sixkids.student.relay.navigation.RelayRoute
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class RelayTaggingSenderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<RelayTaggingSenderState, RelayTaggingSenderEffect>(RelayTaggingSenderState()){
    private val relayId = savedStateHandle.get<Long>(RelayRoute.RELAY_ID_NAME)
    private val question = savedStateHandle.get<String>(RelayRoute.RELAY_QUESTION_NAME)

    fun init() {
        Log.d(TAG, "init: $relayId : $question")
    }
}