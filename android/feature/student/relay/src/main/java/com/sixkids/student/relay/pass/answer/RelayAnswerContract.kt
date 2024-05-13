package com.sixkids.student.relay.pass.answer

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class RelayAnswerState(
    val isLoading: Boolean = false,
    val preQuestion: String = "",
    val nextQuestion: String = ""
): UiState

sealed interface RelayAnswerEffect: SideEffect{
    data object NavigateToPassRelay: RelayAnswerEffect
    data class OnShowSnackBar(val tkn: SnackbarToken): RelayAnswerEffect
}