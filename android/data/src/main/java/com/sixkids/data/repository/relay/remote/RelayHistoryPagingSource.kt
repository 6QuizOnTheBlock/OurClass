package com.sixkids.data.repository.relay.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sixkids.data.api.RelayService
import com.sixkids.data.model.response.toModel
import com.sixkids.model.Relay
import javax.inject.Inject

class RelayHistoryPagingSource @Inject constructor(
    private val relayService: RelayService,
    private val organizationId: Int,
    private val memberId: Int? = null,
) : PagingSource<Int, Relay>() {
    override fun getRefreshKey(state: PagingState<Int, Relay>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Relay> {
        val page = params.key ?: 0
        return try {

            val response = relayService.getRelayHistory(
                organizationId,
                memberId,
                page,
                DEFAULT_SIZE
            )
            val challengeHistory = response.getOrThrow().data.relays.map { it.toModel() }

            LoadResult.Page(
                data = challengeHistory,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (challengeHistory.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val DEFAULT_SIZE = 10
    }

}