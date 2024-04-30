package com.sixkids.data.api

import com.sixkids.data.model.response.ChallengeHistoryResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ChallengeService {
    @GET("challenges")
    suspend fun getChallengeHistory(
        @Query("orgId") organizationId: Int,
        @Query("memberId") memberId: Int? = null,
        @Query("page") page: Int,
    ): ApiResult<ChallengeHistoryResponse>
}
