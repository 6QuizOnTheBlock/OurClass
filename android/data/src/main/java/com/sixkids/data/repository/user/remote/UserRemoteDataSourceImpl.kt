package com.sixkids.data.repository.user.remote

import android.util.Log
import com.sixkids.data.api.SignInService
import com.sixkids.data.model.request.SignInRequest
import com.sixkids.data.model.response.toModel
import com.sixkids.data.repository.user.local.UserLocalDataSource
import com.sixkids.model.JwtToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

private const val TAG = "D107"
class UserRemoteDataSourceImpl @Inject constructor(
    private val signInService: SignInService,
    private val userLocalDataSource: UserLocalDataSource
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

        Log.d(TAG, "signUp: ${multipartBody?.body}")

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

}
