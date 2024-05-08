package com.sixkids.teacher.board.chatting

import com.sixkids.model.Chat
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface ChattingSideEffect : SideEffect{

}

data class ChattingState(
    val isLoading : Boolean = false,
    val organizationName: String = "",
    val memberCount : Int = 0,
    val message: String = "",
    val chatList: List<Chat> = emptyList()
) : UiState