package com.sixkids.student.board.detail

import com.sixkids.model.PostDetail
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface StudentBoardDetailEffect: SideEffect {
    data object RefreshPostDetail: StudentBoardDetailEffect
    data class OnShowSnackbar(val message: String) : StudentBoardDetailEffect
}

data class StudentBoardDetailState(
    val isLoading: Boolean = false,
    val postDetail: PostDetail = PostDetail(),
    val commentText: String = "",
    val selectedCommentId: Long? = null,
) : UiState