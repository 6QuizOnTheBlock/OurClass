package com.sixkids.data.repository.user.remote

import com.sixkids.data.api.SignInService
import com.sixkids.data.model.request.SignInRequest
import com.sixkids.data.model.response.toModel
import com.sixkids.model.JwtToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val signInService: SignInService
) : UserDataSource{
    override suspend fun signIn(idToken: String): JwtToken {
        return signInService.signIn(SignInRequest(idToken)).getOrThrow().data.toModel()
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

        val idToken = RequestBody.create("text/plain".toMediaTypeOrNull(), idToken)
        val defaultImage = RequestBody.create("text/plain".toMediaTypeOrNull(), defaultImage.toString())
        val role = RequestBody.create("text/plain".toMediaTypeOrNull(), role)

        return signInService.signUp(multipartBody, data).getOrThrow().data.toModel()
    }

}
