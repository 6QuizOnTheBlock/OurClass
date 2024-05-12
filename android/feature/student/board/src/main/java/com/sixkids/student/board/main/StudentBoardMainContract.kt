package com.sixkids.student.board.main

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface StudentBoardMainEffect : SideEffect {
    data object NavigateToPostDetail: StudentBoardMainEffect
    data object NavigateToWritePost: StudentBoardMainEffect
    data class OnShowSnackBar(val message : String) : StudentBoardMainEffect
}

data class StudentBoardMainState(
    val isLoding: Boolean = false,
    val classString: String = "",
): UiState