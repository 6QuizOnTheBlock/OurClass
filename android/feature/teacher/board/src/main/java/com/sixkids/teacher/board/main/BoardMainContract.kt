package com.sixkids.teacher.board.main

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class BoardMainState(
    val isLoading: Boolean = false,
    val classString: String = "",
): UiState

sealed interface BoardMainEffect : SideEffect{
    data object NavigateToChatting : BoardMainEffect
}