package com.sixkids.feature.signin.login

import android.content.Context
import android.util.Log
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

private const val TAG = "HONG"
object KakaoManager {
    fun login(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailed: (Throwable?) -> Unit,
    ) {
        Log.d(TAG, "login: ")
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(
                onSuccess = onSuccess,
                onFailed = { error ->
                    Log.d(TAG, "login2: ${error} ${error?.message}")
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
            Log.d(TAG, "loginWithKakaoTalk: ${error}")
            if (token != null) {
                Log.d(TAG, "loginWithKakaoTalk: $token")
                onSuccess(token.accessToken)
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
        Log.d(TAG, "loginWithKakaoAccount: ")
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (token != null) {
                onSuccess(token.accessToken)
            } else {
                onFailed(error)
            }
        }
    }
}