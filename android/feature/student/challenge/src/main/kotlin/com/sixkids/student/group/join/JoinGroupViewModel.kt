package com.sixkids.student.group.join

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.core.bluetooth.BluetoothServer
import com.sixkids.domain.usecase.user.GetATKUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.MemberSimple
import com.sixkids.student.challenge.BuildConfig
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import okio.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "D107"

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val bluetoothServer: BluetoothServer,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getATKUseCase: GetATKUseCase
) : BaseViewModel<JoinGroupState, JoinGroupEffect>(JoinGroupState()) {

    private var eventSource: EventSource? = null

    private val client = OkHttpClient.Builder()
        .addInterceptor {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            }.intercept(it)
        }
        .connectTimeout(10, TimeUnit.MINUTES)
        .readTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .build()

    private val request = Request.Builder()
        .url(BuildConfig.SSE_ENDPOINT)
        .header("Authorization", "Bearer ${runBlocking { getATKUseCase().getOrNull() }}")
        .build()

    private val eventSourceListener = object : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            super.onOpen(eventSource, response)
            Log.d(TAG, "Connection Opened")
        }

        override fun onClosed(eventSource: EventSource) {
            super.onClosed(eventSource)
            Log.d(TAG, "Connection Closed")
        }

        override fun onEvent(
            eventSource: EventSource,
            id: String?,
            type: String?,
            data: String
        ) {
            super.onEvent(eventSource, id, type, data)
            Log.d(TAG, "On Event Received! Data -: $data")
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            Log.d(TAG, "On Failure -: ${response?.body}")
        }
    }

    fun connectSse() = viewModelScope.launch {
        eventSource = EventSources.createFactory(client)
            .newEventSource(request, eventSourceListener)
    }

    fun disconnectSse() {
        eventSource?.cancel()
        eventSource = null
    }

    fun loadUserInfo() {
        viewModelScope.launch {
            loadUserInfoUseCase().onSuccess {
                intent {
                    copy(
                        member = MemberSimple(
                            id = it.id.toLong(),
                            name = it.name,
                            photo = it.photo
                        )
                    )
                }
            }.onFailure {
                postSideEffect(JoinGroupEffect.HandleException(it, ::loadUserInfo))
            }
        }
    }

    fun startAdvertise() {
        viewModelScope.launch {
            bluetoothServer.startAdvertising(uiState.value.member.id)
        }
    }

    fun stopAdvertise() {
        bluetoothServer.stopAdvertising()
    }

}
