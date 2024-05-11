package com.sixkids.data.repository.chatting.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sixkids.data.api.ChattingService
import com.sixkids.data.model.response.toModel
import com.sixkids.data.repository.challenge.remote.ChallengeHistoryPagingSource
import com.sixkids.model.Chat
import javax.inject.Inject

private const val TAG = "D107"
class ChatHistoryPagingSource @Inject constructor(
    private val chattingService: ChattingService,
    private val roomId: Long,
) : PagingSource<Int, Chat>() {
    override fun getRefreshKey(state: PagingState<Int, Chat>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chat> {
        val page = params.key ?: 0
        return try {
            val response = chattingService.getChatList(
                roomId,
                page,
                DEFAULT_SIZE
            )
            val chatHistory = response.getOrThrow().data.messages.map { it.toModel() }
            Log.d(TAG, "load: ${page} -> ${chatHistory.map { it.content }}")
            LoadResult.Page(
                data = chatHistory,
                prevKey = if (chatHistory.isEmpty()) null else page.plus(1),
                nextKey = null
            )
        } catch (e: Exception) {
            Log.d(TAG, "load:  ${e.message}")
            LoadResult.Error(e)
        }
    }

    companion object {
        const val DEFAULT_SIZE = 500
    }

}