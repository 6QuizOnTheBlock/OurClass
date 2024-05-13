package com.sixkids.data.repository.chattingfilter.remote

import com.sixkids.data.api.ChatFilterService
import com.sixkids.data.model.response.ChattingFilterListResponse
import javax.inject.Inject

class ChattingFilterRemoteDataSourceImpl @Inject constructor(
    private val chattingFilterService: ChatFilterService
) : ChattingFilterRemoteDataSource {

    override suspend fun deleteChatFilter(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun createChatFilter(organizationId: Long, badWord: String): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateChatFilter(
        organizationId: Long,
        id: Long,
        badWord: String
    ): Boolean {
        TODO("Not yet implemented")
    }

}