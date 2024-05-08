package com.sixkids.data.repository.challenge.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sixkids.data.api.ChallengeService
import com.sixkids.data.model.response.toModel
import com.sixkids.model.Challenge
import javax.inject.Inject

class ChallengeHistoryPagingSource @Inject constructor(
    private val challengeService: ChallengeService,
    private val organizationId: Int,
    private val memberId: Int? = null,
) : PagingSource<Int, Challenge>() {
    override fun getRefreshKey(state: PagingState<Int, Challenge>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Challenge> {
        val page = params.key ?: 0
        return try {

            val response = challengeService.getChallengeHistory(
                organizationId,
                memberId,
                page,
                DEFAULT_SIZE
            )
            val challengeHistory = response.getOrThrow().data.challenges.map { it.toModel() }

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
