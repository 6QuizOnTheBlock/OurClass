package com.sixkids.teacher.board.post.postlist

import com.sixkids.model.Post
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface PostEffect : SideEffect {
    data object NavigateToPostDetail: PostEffect
    data object NavigateToWritePost: PostEffect
    data class OnShowSnackBar(val message : String) : PostEffect
}

data class PostState(
    val isLoding: Boolean = false,
    val classString: String = "",
    val postList: List<Post> = emptyList(),
): UiState