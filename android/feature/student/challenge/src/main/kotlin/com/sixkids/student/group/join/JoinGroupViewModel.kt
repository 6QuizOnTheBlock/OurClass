package com.sixkids.student.group.join

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.core.bluetooth.BluetoothServer
import com.sixkids.domain.usecase.group.JoinGroupUseCase
import com.sixkids.domain.usecase.user.GetATKUseCase
import com.sixkids.domain.usecase.user.GetMemberSimpleUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.MemberSimple
import com.sixkids.model.SseData
import com.sixkids.model.SseEventType
import com.sixkids.student.challenge.BuildConfig
import com.sixkids.student.group.component.MemberIconItem
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "D107"

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val bluetoothServer: BluetoothServer,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getATKUseCase: GetATKUseCase,
    private val getMemberSimpleUseCase: GetMemberSimpleUseCase,
    private val joinGroupUseCase: JoinGroupUseCase
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
            val sseEventType: SseEventType = SseEventType.valueOf(type ?: "")
            val sseData = Json.decodeFromString<SseData>(data)
            val url = sseData.url
            val roomKey = sseData.data
            when (sseEventType) {
                SseEventType.SSE_CONNECT -> {}
                SseEventType.INVITE_REQUEST -> {
                    if (url == null) return
                    if (roomKey == null) return
                    viewModelScope.launch {
                        getMemberSimpleUseCase(url).onSuccess {
                            intent {
                                copy(
                                    leader = MemberIconItem(
                                        member = it,
                                        showX = false,
                                        isActive = true
                                    ),
                                    roomKey = roomKey,
                                    isJoinedGroup = true
                                )
                            }
                            postSideEffect(JoinGroupEffect.ReceiveInviteRequest)
                        }.onFailure {
                            postSideEffect(JoinGroupEffect.HandleException(it, ::loadUserInfo))
                        }
                    }
                }

                SseEventType.INVITE_RESPONSE -> Log.d(TAG, "onEvent: 초대 응답")
                SseEventType.KICK_MEMBER -> {
                    intent {
                        copy(
                            isJoinedGroup = false
                        )
                    }
                    startAdvertise()
                }
                SseEventType.CREATE_GROUP -> {
                    postSideEffect(JoinGroupEffect.NavigateToChallengeHistory)
                }
            }
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            Log.d(TAG, "On Failure -: ${response?.body} ${t?.message}")
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

    fun answerInvite(joinStatus: Boolean) {
        viewModelScope.launch {
            joinGroupUseCase(uiState.value.roomKey, joinStatus).onSuccess {
                intent {
                    copy(isJoinedGroup = joinStatus)
                }
                if(joinStatus){
                    stopAdvertise()
                }
                closeDialog()
            }.onFailure {
                postSideEffect(JoinGroupEffect.HandleException(it){
                    answerInvite(joinStatus)
                })
            }
        }
    }

    private fun closeDialog() = postSideEffect(JoinGroupEffect.CloseInviteDialog)

}
