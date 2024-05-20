package com.sixkids.student.home.announce.announcelist

import com.sixkids.model.Post
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface StudentAnnounceListEffect : SideEffect {
    data object NavigateToAnnounceDetail: StudentAnnounceListEffect
    data class OnShowSnackBar(val message : String) : StudentAnnounceListEffect
}

data class StudentAnnounceListState(
    val isLoding: Boolean = false,
    val classString: String = "",
    val postList: List<Post> = emptyList(),
): UiState