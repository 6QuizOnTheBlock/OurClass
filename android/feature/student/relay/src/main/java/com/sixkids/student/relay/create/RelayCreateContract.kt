package com.sixkids.student.relay.create

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayCreateState(
    val isLoading: Boolean = false,
) : UiState

sealed interface RelayCreateEffect: SideEffect {
    data object NavigateToRelayResult : RelayCreateEffect
}