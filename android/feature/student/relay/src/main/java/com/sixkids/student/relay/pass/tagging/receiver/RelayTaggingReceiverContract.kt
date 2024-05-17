package com.sixkids.student.relay.pass.tagging.receiver

import com.sixkids.model.RelayReceive
import com.sixkids.student.relay.pass.tagging.RelayNfc
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayTaggingReceiverState(
    val isLoading: Boolean = false,
    val relayId: Long = -1L,
    val relayNfc: RelayNfc = RelayNfc(-1, -1, ""),
    val relayReceive: RelayReceive = RelayReceive("", "", false, 0)
): UiState

sealed interface RelayTaggingReceiverEffect: SideEffect {
    data class OnShowSnackBar(val tkn: SnackbarToken): RelayTaggingReceiverEffect
}