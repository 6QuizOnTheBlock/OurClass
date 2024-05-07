package com.sixkids.data.api

import com.sixkids.data.model.request.ChallengeCreateRequest
import com.sixkids.data.model.response.ChallengeHistoryResponse
import com.sixkids.data.model.response.ChallengeSimpleResponse
import com.sixkids.data.model.response.RunningChallengeResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChallengeService {


    @POST("challenges")
    suspend fun createChallenge(
        @Body challengeCreateRequest: ChallengeCreateRequest
    ): ApiResult<ApiResponse<Int>>

    @GET("challenges")
    suspend fun getChallengeHistory(
        @Query("orgId") organizationId: Int,
        @Query("memberId") memberId: Int? = null,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ApiResult<ApiResponse<ChallengeHistoryResponse>>

    @GET("challenges/running")
    suspend fun getRunningChallenge(
        @Query("organizationId") organizationId: Int,
    ): ApiResult<ApiResponse<RunningChallengeResponse>>

    @GET("challenges/{id}/simple")
    suspend fun getChallengeSimple(
        @Path("id") challengeId: Int
    ): ApiResult<ApiResponse<ChallengeSimpleResponse>>


}
