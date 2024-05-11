package com.sixkids.data.repository.chatting

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sixkids.data.api.ChattingService
import com.sixkids.data.repository.chatting.remote.ChatHistoryPagingSource
import com.sixkids.data.repository.chatting.remote.ChattingRemoteDataSource
import com.sixkids.domain.repository.ChattingRepository
import com.sixkids.model.Chat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChattingRepositoryImpl @Inject constructor(
    private val chattingService: ChattingService,
    private val chattingRemoteDataSource: ChattingRemoteDataSource
) : ChattingRepository {
    override suspend fun getChattingList(roomId: Long): Flow<PagingData<Chat>> =
        Pager(
            config = PagingConfig(ChatHistoryPagingSource.DEFAULT_SIZE),
            pagingSourceFactory = {
                ChatHistoryPagingSource(
                    chattingService,
                    roomId
                )
            }
        ).flow

}