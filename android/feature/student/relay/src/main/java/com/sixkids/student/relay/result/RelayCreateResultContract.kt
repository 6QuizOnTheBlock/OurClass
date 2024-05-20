package com.sixkids.student.relay.result

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayCreateResultState(
    val showResultDialog: Boolean = false,
) : UiState


sealed interface RelayCreateResultEffect : SideEffect {
    data object NavigateToRelayHistory : RelayCreateResultEffect

    data class HandleException(
        val throwable: Throwable, val retry: () -> Unit
    ) : RelayCreateResultEffect

}
