package com.sixkids.student.relay.pass.tagging.sender

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayTaggingSenderState(
    val isLoading: Boolean = false
): UiState

sealed interface RelayTaggingSenderEffect: SideEffect {
    data class NavigateToTaggingResult(val prevMemberName: String, val prevQuestion: String): RelayTaggingSenderEffect
    data class OnShowSnackBar(val tkn: SnackbarToken): RelayTaggingSenderEffect
}