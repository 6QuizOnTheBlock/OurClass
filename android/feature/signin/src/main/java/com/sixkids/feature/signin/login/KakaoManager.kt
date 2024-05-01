package com.sixkids.feature.signin.login

import android.content.Context
import android.util.Log
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

private const val TAG = "D107"

object KakaoManager {
    fun login(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(
                onSuccess = onSuccess,
                onFailed = { error ->
                    if (error is AuthError || (error is ClientError && error.reason != ClientErrorCause.Cancelled)) {
                        loginWithKakaoAccount(
                            onSuccess = onSuccess,
                            onFailed = onFailed,
                            context = context,
                        )
                    }
                },
                context = context,
            )
        } else {
            loginWithKakaoAccount(
                onSuccess = onSuccess,
                onFailed = onFailed,
                context = context,
            )
        }
    }

    private fun loginWithKakaoTalk(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            Log.d(TAG, "loginWithKakaoTalk:$token")
            if (token != null && token.idToken != null){
                onSuccess(token.idToken!!)
            } else {
                onFailed(error)
            }
        }
    }

    private fun loginWithKakaoAccount(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (token != null && token.idToken != null){
                onSuccess(token.idToken!!)
            } else {
                onFailed(error)
            }
        }
    }
}