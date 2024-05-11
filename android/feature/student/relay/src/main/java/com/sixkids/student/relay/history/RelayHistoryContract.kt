package com.sixkids.student.relay.history

import com.sixkids.model.RunningRelay
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayHistoryState(
    val isLoading: Boolean = false,
    val runningRelay: RunningRelay? = null,
    val totalRelayCount: Int = 0,
) : UiState

sealed interface RelayHistoryEffect : SideEffect {
    data class NavigateToRelayDetail(val relayId: Long, val groupId: Long? = null) : RelayHistoryEffect
    data object NavigateToCreateRelay : RelayHistoryEffect
    data object NavigateToJoinRelay : RelayHistoryEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : RelayHistoryEffect
}