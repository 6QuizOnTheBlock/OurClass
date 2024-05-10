package com.sixkids.teacher.board.postdetail

import com.sixkids.model.PostDetail
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface PostDetailEffect: SideEffect{
    data object RefreshPostDetail: PostDetailEffect
    data class OnShowSnackbar(val message: String) : PostDetailEffect
}

data class PostDetailState(
    val isLoading: Boolean = false,
    val postDetail: PostDetail = PostDetail(),
    val commentText: String = "",
    val selectedCommentId: Long? = null,
) : UiState