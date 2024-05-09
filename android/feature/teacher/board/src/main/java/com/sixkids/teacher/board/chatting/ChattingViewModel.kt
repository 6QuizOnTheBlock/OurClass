package com.sixkids.teacher.board.chatting

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.user.GetATKUseCase
import com.sixkids.domain.usecase.user.GetUserInfoUseCase
import com.sixkids.model.ChatMessage
import com.sixkids.model.UserInfo
import com.sixkids.ui.base.BaseViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.conversions.convertAndSend
import org.hildan.krossbow.stomp.conversions.moshi.withMoshi
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.use
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import javax.inject.Inject

private const val TAG = "D107"

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val getATKUseCase: GetATKUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel<ChattingState, ChattingSideEffect>(ChattingState()) {

    private var roomId = 1
    private lateinit var tkn: String
    private lateinit var userInfo: UserInfo

    private lateinit var stompSession : StompSession
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @SuppressLint("CheckResult")
    fun initStomp() {
        viewModelScope.launch {
            try {
                // Stomp 연결
                val tknJob = async { getATKUseCase().getOrThrow() }
                val roomIdJob = async { getSelectedOrganizationIdUseCase().getOrThrow() }
                val userInfoJob = async { getUserInfoUseCase().getOrThrow() }

                tkn = tknJob.await()
//                roomId = roomIdJob.await()
                userInfo = userInfoJob.await()


                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .build()

                val wsClient = OkHttpWebSocketClient(okHttpClient)

                val client = StompClient(wsClient)

                stompSession = client.connect(
                    STOMP_URL,
                    customStompConnectHeaders = mapOf(
                        HEADER_AUTHORIZATION to tkn,
                        HEADER_ROOM_ID to roomId.toString()
                    )
                )
            } catch (e: Exception) {
                Log.d(TAG, "initStomp: ${e.message}")
            }
        }
    }

    fun updateMessage(message: String) {
        intent { copy(message = message) }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            stompSession.withMoshi(moshi).use { session ->
                session.convertAndSend(
                    StompSendHeaders(
                        destination = SEND_URL,
                        customHeaders = mapOf(
                            HEADER_AUTHORIZATION to tkn
                        )
                    ),
                    ChatMessage(roomId.toLong(), userInfo.photo, message)
                )
            }
        }
        intent { copy(message = "") }
    }

    fun cancelStomp() {
        try {

        } catch (e: Exception) {
            Log.d(TAG, "cancelStomp: ${e.message}")
        }
    }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_ROOM_ID = "roomId"
        const val HEADER_DESTINATION = "destination"

        const val STOMP_URL = "wss://k10d107.p.ssafy.io/api/ws-stomp/websocket"
        const val SEND_URL = "/publish/chat/message"
        const val SUBSCRIBE_URL = "/subscribe/public/"
    }

}