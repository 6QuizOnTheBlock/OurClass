package com.sixkids.ulban

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

private const val TAG = "HONG"
@HiltAndroidApp
class UlbanApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate: ${Utility.getKeyHash(this)}")
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}
