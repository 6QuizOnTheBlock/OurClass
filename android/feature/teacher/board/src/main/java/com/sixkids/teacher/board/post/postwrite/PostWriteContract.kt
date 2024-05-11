package com.sixkids.teacher.board.post.postwrite

import android.graphics.Bitmap
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface PostWriteEffect: SideEffect{
    data object NavigateBack : PostWriteEffect
    data class OnShowSnackbar(val message: String) : PostWriteEffect
}

data class PostWriteState(
    val isLoading: Boolean = false,
    val title: String = "",
    val content: String = "",
    val anonymousChecked: Boolean = false,
    val photo: Bitmap? = null
): UiState