package com.sixkids.teacher.board.main

import com.sixkids.ui.base.SideEffect

data class BoardMainState(
    val isLoading: Boolean = false,
    val classString: String = "",
)

sealed interface BoardMainEffect : SideEffect{
    data object NavigateToChatting : BoardMainEffect
}