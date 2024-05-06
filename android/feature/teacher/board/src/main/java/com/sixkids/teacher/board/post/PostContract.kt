package com.sixkids.teacher.board.post

import com.sixkids.model.Post

data class PostState(
    val classString: String = "",
    val postList: List<Post> = emptyList(),
)