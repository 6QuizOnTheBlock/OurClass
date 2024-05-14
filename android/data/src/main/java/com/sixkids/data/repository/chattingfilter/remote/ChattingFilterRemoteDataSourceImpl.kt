package com.sixkids.data.repository.chattingfilter.remote

import com.sixkids.data.api.ChatFilterService
import com.sixkids.data.model.request.ChatFilterRequest
import com.sixkids.data.model.response.ChattingFilterListResponse
import javax.inject.Inject

class ChattingFilterRemoteDataSourceImpl @Inject constructor(
    private val chattingFilterService: ChatFilterService
) : ChattingFilterRemoteDataSource {

    override suspend fun deleteChatFilter(id: Long): Boolean {
        return chattingFilterService.deleteChatFilter(id).getOrThrow().data
    }

    override suspend fun createChatFilter(organizationId: Long, badWord: String): Long {
        return chattingFilterService.createChatFilter(organizationId, ChatFilterRequest(badWord))
            .getOrThrow().data
    }

    override suspend fun updateChatFilter(
        organizationId: Long,
        id: Long,
        badWord: String
    ): Boolean {
        return chattingFilterService.updateChatFilter(
            id,
            organizationId,
            ChatFilterRequest(badWord)
        ).getOrThrow().data
    }

}