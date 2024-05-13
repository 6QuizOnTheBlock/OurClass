package com.sixkids.data.api

import com.sixkids.data.model.response.GroupMatchingRoomResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.POST
import retrofit2.http.Query

interface GroupService {
    @POST("challenges/groups/matchingroom")
    fun createMatchingRoom(
        @Query("challengeId") challengeId: Long,
    ): ApiResult<ApiResponse<GroupMatchingRoomResponse>>
}
