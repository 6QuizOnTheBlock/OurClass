package com.sixkids.teacher.board.post

import com.sixkids.model.PostItem

data class PostState(
    val classString: String,
    val postList: List<PostItem>
)