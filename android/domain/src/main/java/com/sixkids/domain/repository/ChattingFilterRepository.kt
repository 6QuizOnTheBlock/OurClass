package com.sixkids.domain.repository

import androidx.paging.PagingData
import com.sixkids.model.ChatFilterWord
import kotlinx.coroutines.flow.Flow

interface ChattingFilterRepository {

    suspend fun getChattingFilters(
        organizationId: Int
    ): Flow<PagingData<ChatFilterWord>>

    suspend fun deleteChatFilter(
        id: Long
    ): Boolean

    suspend fun createChatFilter(
        organizationId: Long,
        badWord: String,
    ): Long

    suspend fun updateChatFilter(
        organizationId: Long,
        id: Long,
        badWord: String,
    ): Boolean
}