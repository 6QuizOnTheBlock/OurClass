package com.sixkids.teacher.board.announce.announcewrite

import android.graphics.Bitmap
import com.sixkids.teacher.board.post.postwrite.PostWriteEffect
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface AnnounceWriteEffect: SideEffect{
    data object NavigateBack : AnnounceWriteEffect
    data class OnShowSnackbar(val message: String) : AnnounceWriteEffect
}

data class AnnounceWriteState(
    val isLoading: Boolean = false,
    val title: String = "",
    val content: String = "",
    val anonymousChecked: Boolean = false,
    val photo: Bitmap? = null
): UiState