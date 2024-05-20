package com.sixkids.student.home.chatting

import com.sixkids.model.Chat
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface StudentChattingSideEffect : SideEffect{

}

data class StudentChattingState(
    val isLoading : Boolean = false,
    val organizationName: String = "",
    val memberCount : Int = 0,
    val memberId: Int = 0,
    val message: String = "",
    val chatList: List<Chat> = emptyList()
) : UiState