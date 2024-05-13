package com.sixkids.data.repository.chattingfilter

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sixkids.data.api.ChatFilterService
import com.sixkids.data.model.response.toModel
import com.sixkids.data.repository.chattingfilter.remote.ChattingFilterPagingSource
import com.sixkids.data.repository.chattingfilter.remote.ChattingFilterRemoteDataSource
import com.sixkids.domain.repository.ChattingFilterRepository
import com.sixkids.model.ChatFilterWord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChattingFilterRepositoryImpl @Inject constructor(
    private val chattingFilterRemoteDataSource: ChattingFilterRemoteDataSource,
    private val chatFilterService: ChatFilterService
) : ChattingFilterRepository {
    override suspend fun getChattingFilters(
        organizationId: Int,
        page: Int,
        size: Int
    ): Flow<PagingData<ChatFilterWord>> {
        return Pager(
            config = PagingConfig(ChattingFilterPagingSource.DEFAULT_SIZE),
            pagingSourceFactory = {
                ChattingFilterPagingSource(
                    chatFilterService,
                    organizationId
                )
            }
        ).flow.map {pagingData ->
            pagingData.map {
                chattingFilterResponse -> chattingFilterResponse.toModel()
            }
        }
    }

    override suspend fun deleteChatFilter(id: Long): Boolean {
        return chattingFilterRemoteDataSource.deleteChatFilter(id)
    }

    override suspend fun createChatFilter(organizationId: Long, badWord: String): Long {
        return chattingFilterRemoteDataSource.createChatFilter(organizationId, badWord)
    }

    override suspend fun updateChatFilter(
        organizationId: Long,
        id: Long,
        badWord: String
    ): Boolean {
        return chattingFilterRemoteDataSource.updateChatFilter(organizationId, id, badWord)
    }
}