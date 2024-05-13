package com.sixkids.student.relay.pass.tagging.receiver

import com.sixkids.student.relay.pass.tagging.RelayNfc
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayTaggingReceiverState(
    val isLoading: Boolean = false,
    val relayId: Long = -1L,
    val relayNfc: RelayNfc = RelayNfc(-1, -1, ""),
): UiState

sealed interface RelayTaggingReceiverEffect: SideEffect {
    data class NavigateToTaggingResult(val prevMemberName: String, val prevQuestion: String): RelayTaggingReceiverEffect
    data class OnShowSnackBar(val tkn: SnackbarToken): RelayTaggingReceiverEffect
}