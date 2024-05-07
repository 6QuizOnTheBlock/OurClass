package com.sixkids.teacher.board.chatting

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface ChattingSideEffect : SideEffect{

}

data class ChattingState(
    val isLoading : Boolean = false,
) : UiState