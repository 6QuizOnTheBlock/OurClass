package com.sixkids.data.api

import com.sixkids.data.model.response.StudentDetailResponse
import com.sixkids.data.model.response.StudentHomeResponse
import com.sixkids.data.model.response.StudentWithRelationScoreResponse
import com.sixkids.data.network.ApiResponse
import com.sixkids.data.network.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MemberOrgService {
    @GET("organizations/{id}/home")
    suspend fun getStudentHomeInfo(
        @Path("id") organizationId: Long
    ): ApiResult<ApiResponse<StudentHomeResponse>>

    @GET("organizations/{id}")
    suspend fun getMemberDetail(
        @Path("id") organizationId: Long,
        @Query("memberId") memberId: Long
    ): ApiResult<ApiResponse<StudentDetailResponse>>

    @GET("organizations/{id}/relations")
    suspend fun getRelationSimple(
        @Path("id") organizationId: Long,
        @Query("memberId") memberId: Int,
        @Query("limit") relationType: Int? = null
    ): ApiResult<ApiResponse<List<StudentWithRelationScoreResponse>>>

}