package com.sixkids.data.repository.chattingfilter.remote

import com.sixkids.data.model.response.ChattingFilterListResponse

interface ChattingFilterRemoteDataSource {

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