package com.sixkids.teacher.board.postdetail

import com.sixkids.model.PostDetail

data class PostDetailState(
    val isLoading: Boolean,
    val postDetail: PostDetail,
)