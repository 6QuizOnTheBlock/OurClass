package com.sixkids.teacher.board.chatting

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.user.GetATKUseCase
import com.sixkids.domain.usecase.user.GetUserInfoUseCase
import com.sixkids.model.Chat
import com.sixkids.model.ChatMessage
import com.sixkids.model.UserInfo
import com.sixkids.ui.base.BaseViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.conversions.convertAndSend
import org.hildan.krossbow.stomp.conversions.moshi.withMoshi
import org.hildan.krossbow.stomp.frame.StompFrame
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import javax.inject.Inject
import com.sixkids.teacher.board.BuildConfig

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

    private lateinit var stompSession: StompSession
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private lateinit var newChatMessage: Flow<StompFrame.Message>

    private lateinit var chattingList: List<Chat>

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

                intent { copy(memberId = userInfo.id) }

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
                    BuildConfig.STOMP_ENDPOINT,
                    customStompConnectHeaders = mapOf(
                        HEADER_AUTHORIZATION to tkn,
                        HEADER_ROOM_ID to roomId.toString()
                    )
                ).withMoshi(moshi)

                newChatMessage = stompSession.subscribe(
                    StompSubscribeHeaders(
                        destination = "$SUBSCRIBE_URL$roomId",
                        customHeaders = mapOf(
                            HEADER_AUTHORIZATION to tkn
                        )
                    )
                )

                newChatMessage.collect {
                    val chatMessage = moshi.adapter(Chat::class.java).fromJson(it.bodyAsText)
                    intent { copy(chatList = chatList + chatMessage!!) }
                }

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
            stompSession.withMoshi(moshi).convertAndSend(
                StompSendHeaders(
                    destination = SEND_URL,
                    customHeaders = mapOf(
                        HEADER_AUTHORIZATION to tkn
                    )
                ),
                ChatMessage(roomId.toLong(), userInfo.photo, message)
            )
        }
        intent { copy(message = "") }
    }

    fun cancelStomp() {
        try {
            viewModelScope.launch {
                stompSession.disconnect()
            }
        } catch (e: Exception) {
            Log.d(TAG, "cancelStomp: ${e.message}")
        }
    }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_ROOM_ID = "roomId"

        const val SEND_URL = "/publish/chat/message"
        const val SUBSCRIBE_URL = "/subscribe/public/"
    }

}