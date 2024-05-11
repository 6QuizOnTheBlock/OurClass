package com.sixkids.teacher.board.announce.announcedetail

import com.sixkids.model.PostDetail
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface AnnounceDetailEffect : SideEffect {
    data class RefreshAnnounceDetail(val announceId: Long) : AnnounceDetailEffect
    data class OnShowSnackbar(val message: String) : AnnounceDetailEffect
}

data class AnnounceDetailState(
    val isLoading: Boolean = false,
    val postDetail: PostDetail = PostDetail(),
    val commentText: String = "",
    val selectedCommentId: Long? = null,
) : UiState