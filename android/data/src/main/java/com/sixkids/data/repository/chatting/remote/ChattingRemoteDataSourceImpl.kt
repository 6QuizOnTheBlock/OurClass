package com.sixkids.data.repository.chatting.remote

import com.sixkids.data.api.ChattingService
import javax.inject.Inject

class ChattingRemoteDataSourceImpl @Inject constructor(
    private val chattingService: ChattingService
) : ChattingRemoteDataSource{

}