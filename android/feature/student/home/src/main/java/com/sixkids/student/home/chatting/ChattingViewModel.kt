package com.sixkids.student.home.chatting

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sixkids.domain.usecase.chatting.GetChattingHistoryUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.user.GetATKUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.Chat
import com.sixkids.model.ChatMessage
import com.sixkids.model.UserInfo
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.hildan.krossbow.stomp.conversions.convertAndSend
import org.hildan.krossbow.stomp.conversions.moshi.withMoshi
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val getATKUseCase: GetATKUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getChattingHistoryUseCase: GetChattingHistoryUseCase
) : BaseViewModel<StudentChattingState, StudentChattingSideEffect>(
    StudentChattingState()
) {

    private var roomId = 1L
    private lateinit var tkn: String
    private lateinit var userInfo: UserInfo

    private lateinit var stompSession: org.hildan.krossbow.stomp.StompSession
    private val moshi: com.squareup.moshi.Moshi = com.squareup.moshi.Moshi.Builder()
        .addLast(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
        .build()
    private lateinit var newChatMessage: Flow<org.hildan.krossbow.stomp.frame.StompFrame.Message>

    private lateinit var chattingList: List<Chat>

    var originalChatList: Flow<PagingData<Chat>>? = null

    @SuppressLint("CheckResult")
    fun initStomp() {
        viewModelScope.launch {
            try {
                loadLocalData()

                originalChatList =
                    getChattingHistoryUseCase(roomId).cachedIn(viewModelScope)



            } catch (e: Exception) {
                Log.d(com.sixkids.teacher.board.chatting.TAG, "initStomp: ${e.message}")
            }
        }
    }

    private fun loadLocalData() {
        viewModelScope.launch {
            val tknJob = async { getATKUseCase().getOrThrow() }
            val roomIdJob = async { getSelectedOrganizationIdUseCase().getOrThrow() }
            val userInfoJob = async { loadUserInfoUseCase().getOrThrow() }

            tkn = tknJob.await()
            roomId = roomIdJob.await().toLong()
            userInfo = userInfoJob.await()

             connectStomp()

            intent { copy(memberId = userInfo.id) }
        }
    }

    suspend fun connectStomp(){
        viewModelScope.launch {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(
                    okhttp3.logging.HttpLoggingInterceptor().apply {
                        level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()

            val client = org.hildan.krossbow.stomp.StompClient(
                org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient(okHttpClient)
            )

            stompSession = client.connect(
                com.sixkids.teacher.board.BuildConfig.STOMP_ENDPOINT,
                customStompConnectHeaders = mapOf(
                    HEADER_AUTHORIZATION to tkn,
                    HEADER_ROOM_ID to roomId.toString()
                )
            ).withMoshi(moshi)

            newChatMessage = stompSession.subscribe(
                org.hildan.krossbow.stomp.headers.StompSubscribeHeaders(
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


        }
    }

    fun updateMessage(message: String) {
        intent { copy(message = message) }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            Log.d(com.sixkids.teacher.board.chatting.TAG, "sendMessage: ${userInfo.photo}")
            stompSession.withMoshi(moshi).convertAndSend(
                org.hildan.krossbow.stomp.headers.StompSendHeaders(
                    destination = SEND_URL,
                    customHeaders = mapOf(
                        HEADER_AUTHORIZATION to tkn
                    )
                ),
                ChatMessage(roomId, userInfo.photo, message)
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
            Log.d(com.sixkids.teacher.board.chatting.TAG, "cancelStomp: ${e.message}")
        }
    }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_ROOM_ID = "roomId"

        const val SEND_URL = "/publish/chat/message"
        const val SUBSCRIBE_URL = "/subscribe/public/"
    }

}