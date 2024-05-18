package com.sixkids.data.repository.chattingfilter.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sixkids.data.api.ChatFilterService
import com.sixkids.data.api.ChattingService
import com.sixkids.data.model.response.ChattingFilterListResponse
import com.sixkids.data.model.response.ChattingFilterResponse
import javax.inject.Inject

class ChattingFilterPagingSource @Inject constructor(
    private val chatFilterService: ChatFilterService,
    private val organizationId: Int,
    ): PagingSource<Int, ChattingFilterResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ChattingFilterResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChattingFilterResponse> {
        val page = params.key ?: 0
        return try {
            val response = chatFilterService.getChatFilters(
                organizationId,
                page,
                DEFAULT_SIZE
            ).getOrThrow().data.words

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
        const val DEFAULT_SIZE = 30
    }
}