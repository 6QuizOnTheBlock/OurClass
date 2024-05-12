package com.sixkids.student.relay.create

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayCreateState(
    val isLoading: Boolean = false,
    val question: String = "",
    val orgId: Int = -1
) : UiState

sealed interface RelayCreateEffect: SideEffect {
    data class NavigateToRelayResult(val newRelayId: Long) : RelayCreateEffect
    data class OnShowSnackBar(val tkn : SnackbarToken) : RelayCreateEffect
}