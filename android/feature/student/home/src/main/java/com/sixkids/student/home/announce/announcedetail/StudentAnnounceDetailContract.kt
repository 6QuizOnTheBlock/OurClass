package com.sixkids.student.home.announce.announcedetail

import com.sixkids.model.PostDetail
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface StudentAnnounceDetailEffect : SideEffect {
    data object RefreshAnnounceDetail : StudentAnnounceDetailEffect
    data class OnShowSnackbar(val message: String) : StudentAnnounceDetailEffect
}

data class StudentAnnounceDetailState(
    val isLoading: Boolean = false,
    val postDetail: PostDetail = PostDetail(),
    val commentText: String = "",
    val selectedCommentId: Long? = null,
) : UiState