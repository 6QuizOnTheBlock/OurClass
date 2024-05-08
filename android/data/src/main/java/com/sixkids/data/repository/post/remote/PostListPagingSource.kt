package com.sixkids.data.repository.post.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sixkids.data.api.PostService
import com.sixkids.model.Post
import javax.inject.Inject

class PostListPagingSource @Inject constructor(
    private val postService: PostService,
    private val organizationId: Int,
    private val memberId: Int? = null,
    private val category: String,
){

}