package com.sixkids.data.repository.user.remote

import com.sixkids.data.api.MemberOrgService
import com.sixkids.data.api.MemberService
import com.sixkids.data.api.SignInService
import com.sixkids.data.model.request.FcmRequest
import com.sixkids.data.model.request.SignInRequest
import com.sixkids.data.model.response.toModel
import com.sixkids.data.repository.user.local.UserLocalDataSource
import com.sixkids.model.JwtToken
import com.sixkids.model.MemberSimple
import com.sixkids.model.UserInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val signInService: SignInService,
    private val memberService: MemberService,
    private val userLocalDataSource: UserLocalDataSource,
    private val memberOrgService: MemberOrgService
) : UserRemoteDataSource{
    override suspend fun signIn(idToken: String): JwtToken {
        val response = signInService.signIn(SignInRequest(idToken))
        if (response.getOrNull() != null) {
            userLocalDataSource.saveRole(response.getOrNull()?.data?.role ?: "error")
        }
        return response.getOrThrow().data.toModel()
    }

    override suspend fun signUp(
        file: File?,
        idToken: String,
        defaultImage: Int,
        role: String
    ): JwtToken {
        val data = HashMap<String, RequestBody>()

        var multipartBody : MultipartBody.Part? = null

        if (file != null) {
            val image = file.asRequestBody("image/*".toMediaTypeOrNull())
            multipartBody = MultipartBody.Part.createFormData("file", file.name, image)
        }

        val _idToken = RequestBody.create("text/plain".toMediaTypeOrNull(), idToken)
        val _defaultImage = RequestBody.create("text/plain".toMediaTypeOrNull(), defaultImage.toString())
        val _role = RequestBody.create("text/plain".toMediaTypeOrNull(), role)

        data["idToken"] = _idToken
        data["defaultImage"] = _defaultImage
        data["role"] = _role

        val response = signInService.signUp(multipartBody, data)
        if (response.getOrNull() != null) {
            userLocalDataSource.saveRole(response.getOrNull()?.data?.role ?: "error")
        }
        return response.getOrThrow().data.toModel()
    }

    override suspend fun getMemberInfo(): UserInfo {
        val response = memberService.getMemberInfo()
        if (response.getOrNull() != null) {
            userLocalDataSource.saveUserId(response.getOrNull()?.data?.id ?: 0)
            userLocalDataSource.saveUserName(response.getOrNull()?.data?.name ?: "")
            userLocalDataSource.saveUserProfileImage(response.getOrNull()?.data?.photo ?: "")
            userLocalDataSource.saveUserInfo(
                response.getOrNull()?.data?.id ?: 0,
                response.getOrNull()?.data?.name ?: "",
                response.getOrNull()?.data?.email ?: "",
                response.getOrNull()?.data?.photo ?: "",
                response.getOrNull()?.data?.role ?: ""
            )
        }
        return response.getOrThrow().data.toModel()
    }

    override suspend fun getMemberSimple(id: Long): MemberSimple =
        memberService.getMemberInfoById(id).getOrThrow().data.toModel()

    override suspend fun updateMemberProfilePhoto(file: File?, defaultImage: Int): String {
        val data = HashMap<String, RequestBody>()

        var multipartBody : MultipartBody.Part? = null

        if (file != null) {
            val image = file.asRequestBody("image/*".toMediaTypeOrNull())
            multipartBody = MultipartBody.Part.createFormData("file", file.name, image)
        }

        val _defaultImage = RequestBody.create("text/plain".toMediaTypeOrNull(), defaultImage.toString())
        data["defaultImage"] = _defaultImage

        val response = memberService.updateMemberProfilePhoto(multipartBody, data)

        if (response.getOrNull() != null) {
            userLocalDataSource.saveUserProfileImage(response.getOrNull()?.data?.photoImageUrl ?: "")
        }

        return response.getOrThrow().data.photoImageUrl
    }

    override suspend fun updateFCMToken(fcmToken: String) = memberService.updateFCMToken(FcmRequest(fcmToken)).getOrThrow().data

    override suspend fun autoSignIn(): JwtToken {
        val response = memberService.autoSignIn()
        if (response.getOrNull() != null) {
            userLocalDataSource.saveRole(response.getOrNull()?.data?.role ?: "error")
        }
        return response.getOrThrow().data.toModel()
    }

    override suspend fun getStudentHomeInfo(organizationId: Long) = memberOrgService.getStudentHomeInfo(organizationId).getOrThrow().data
}
