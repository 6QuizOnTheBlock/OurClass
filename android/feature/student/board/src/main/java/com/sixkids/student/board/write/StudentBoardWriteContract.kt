package com.sixkids.student.board.write

import android.graphics.Bitmap
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface StudentBoardWriteEffect: SideEffect {
    data object NavigateBack : StudentBoardWriteEffect
    data class OnShowSnackbar(val message: String) : StudentBoardWriteEffect
}

data class StudentBoardWriteState(
    val isLoading: Boolean = false,
    val title: String = "",
    val content: String = "",
    val anonymousChecked: Boolean = false,
    val photo: Bitmap? = null
): UiState