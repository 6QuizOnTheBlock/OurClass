package com.sixkids.student.relay.detail

import com.sixkids.model.RelayDetail
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayDetailState(
    val isLoading: Boolean = false,
    val relayDetail: RelayDetail = RelayDetail(),
) : UiState

sealed interface RelayDetailSideEffect : SideEffect{
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) :
        RelayDetailSideEffect
}