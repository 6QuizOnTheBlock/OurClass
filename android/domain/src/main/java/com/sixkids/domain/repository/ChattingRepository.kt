package com.sixkids.domain.repository

import androidx.paging.PagingData
import com.sixkids.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChattingRepository {
    suspend fun getChattingList(roomId: Long): Flow<PagingData<Chat>>
}