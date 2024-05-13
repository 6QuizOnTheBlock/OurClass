package com.sixkids.student.relay.pass.tagging.receiver

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayTaggingReceiverState(
    val isLoading: Boolean = false
): UiState

sealed interface RelayTaggingReceiverEffect: SideEffect {
    data class NavigateToTaggingResult(val prevMemberName: String, val prevQuestion: String): RelayTaggingReceiverEffect
    data class OnShowSnackBar(val tkn: SnackbarToken): RelayTaggingReceiverEffect
}