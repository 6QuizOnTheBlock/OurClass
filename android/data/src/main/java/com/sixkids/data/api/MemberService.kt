package com.sixkids.data.api

import com.sixkids.data.model.request.FcmRequest
import com.sixkids.data.model.response.ApiResponse
import com.sixkids.data.model.response.MemberInfoResponse
import com.sixkids.data.model.response.MemberSimpleInfoResponse
import com.sixkids.data.model.response.SignInResponse
import com.sixkids.data.model.response.UpdateProfilePhotoResponse
import com.sixkids.data.network.ApiResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface MemberService {
    @GET("members/")
    suspend fun getMemberInfo(): ApiResult<ApiResponse<MemberInfoResponse>>

    @GET("members/{id}")
    suspend fun getMemberInfoById(
        @Path("id") id: Long
    ): ApiResult<ApiResponse<MemberSimpleInfoResponse>>

    @Multipart
    @PATCH("members/photo")
    suspend fun updateMemberProfilePhoto(
        @Part file: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
    ): ApiResult<ApiResponse<UpdateProfilePhotoResponse>>

    @PATCH("members/token")
    suspend fun autoSignIn() : ApiResult<ApiResponse<SignInResponse>>

    @POST("members/fcm")
    suspend fun updateFCMToken(
        @Body fcmToken: FcmRequest
    ): ApiResult<ApiResponse<Unit>>
}
