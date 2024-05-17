package com.sixkids.data.api

import com.sixkids.data.model.response.GroupMatchingRoomResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GroupService {
    @POST("challenges/groups/matchingroom")
    suspend fun createMatchingRoom(
        @Query("challengeId") challengeId: Long,
    ): ApiResult<ApiResponse<GroupMatchingRoomResponse>>

   @GET("challenges/groups/invite")
    suspend fun inviteFriend(
        @Query("key") key: String,
        @Query("memberId") memberId: Long,
    ): ApiResult<ApiResponse<Unit>>

    @DELETE("challenges/groups/matching")
    suspend fun deportFriend(
        @Query("key") key: String,
        @Query("memberId") memberId: Long,
    ): ApiResult<ApiResponse<Unit>>

    @POST("challenges/groups/join")
    suspend fun joinGroup(
        @Query("key") key: String,
        @Query("joinStatus") joinStatus : Boolean,
    ): ApiResult<ApiResponse<Unit>>
}
