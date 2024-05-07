package com.sixkids.teacher.challenge.result

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ResultState(
    val showResultDialog: Boolean = false,
) : UiState


sealed interface ResultEffect : SideEffect {
    data object ShowResultDialog : ResultEffect

}
