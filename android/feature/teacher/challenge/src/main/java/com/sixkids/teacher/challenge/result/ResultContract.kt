package com.sixkids.teacher.challenge.result

import com.sixkids.model.Challenge
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ResultState(
    val showResultDialog: Boolean = false,
    val challenge: Challenge = Challenge()
) : UiState


sealed interface ResultEffect : SideEffect {
    data object ShowResultDialog : ResultEffect
    data class HandleException(
        val throwable: Throwable, val retry: () -> Unit
    ) : ResultEffect

}
