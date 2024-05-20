package com.sixkids.student.relay.result

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RelayCreateResultViewModel @Inject constructor(

): BaseViewModel<RelayCreateResultState, RelayCreateResultEffect>(
    RelayCreateResultState()
){

    fun navigateToChallengeHistory() {
        postSideEffect(RelayCreateResultEffect.NavigateToRelayHistory)
    }
}