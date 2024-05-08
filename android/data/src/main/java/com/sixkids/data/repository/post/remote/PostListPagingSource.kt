package com.sixkids.data.repository.post.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sixkids.data.api.PostService
import com.sixkids.data.model.response.PostResponse
import javax.inject.Inject

class PostListPagingSource @Inject constructor(
    private val postService: PostService,
    private val organizationId: Int,
    private val memberId: Int? = null,
    private val category: String,
) : PagingSource<Int, PostResponse>() {
    override fun getRefreshKey(state: PagingState<Int, PostResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostResponse> {
        val page = params.key ?: 0
        return try {
            val response = postService.getPosts(
                organizationId,
                memberId,
                page,
                DEFAULT_SIZE,
                category
            ).getOrThrow().data.posts

            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val DEFAULT_SIZE = 10
    }
}