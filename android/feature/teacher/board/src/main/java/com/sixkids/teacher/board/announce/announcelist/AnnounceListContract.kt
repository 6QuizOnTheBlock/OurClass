package com.sixkids.teacher.board.announce.announcelist

import com.sixkids.model.Post
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface AnnounceListEffect : SideEffect{
    data object NavigateToAnnounceDetail: AnnounceListEffect
    data object NavigateToWriteAnnounce: AnnounceListEffect
    data class OnShowSnackBar(val message : String) : AnnounceListEffect
}

data class AnnounceListState(
    val isLoding: Boolean = false,
    val classString: String = "",
    val postList: List<Post> = emptyList(),
): UiState