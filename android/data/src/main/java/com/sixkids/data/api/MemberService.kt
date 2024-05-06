package com.sixkids.data.api

import com.sixkids.data.model.response.ApiResponse
import com.sixkids.data.model.response.MemberInfoResponse
import com.sixkids.data.model.response.UpdateProfilePhotoResponse
import com.sixkids.data.network.ApiResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.PartMap

interface MemberService {
    @GET("members/")
    suspend fun getMemberInfo(): ApiResult<ApiResponse<MemberInfoResponse>>

    @Multipart
    @PATCH("members/photo")
    suspend fun updateMemberProfilePhoto(
        @Part file: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
    ): ApiResult<ApiResponse<UpdateProfilePhotoResponse>>

}