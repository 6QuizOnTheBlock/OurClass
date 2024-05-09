package com.sixkids.teacher.board.chatting

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.user.GetATKUseCase
import com.sixkids.domain.usecase.user.GetUserInfoUseCase
import com.sixkids.model.UserInfo
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompCommand
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage
import javax.inject.Inject

private const val TAG = "D107"

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val getATKUseCase: GetATKUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel<ChattingState, ChattingSideEffect>(ChattingState()) {

    private val stompUrl = "ws://k10d107.p.ssafy.io:8085/api/ws-stomp/websocket"
    private var roomId = 1
    private lateinit var tkn: String
    private lateinit var userInfo: UserInfo

    private lateinit var stomp: StompClient
    private lateinit var stompConnection: Disposable
    private lateinit var topic: Disposable



    @SuppressLint("CheckResult")
    fun initStomp() {
        viewModelScope.launch {
            try {
                // Stomp 연결
                val tknJob = async { getATKUseCase().getOrThrow() }
                val roomIdJob = async { getSelectedOrganizationIdUseCase().getOrThrow() }

                stomp = Stomp.over(
                    Stomp.ConnectionProvider.OKHTTP,
                    stompUrl
                )

                tkn = tknJob.await()
//                roomId = roomIdJob.await()

                val connectionHeaderList = arrayListOf<StompHeader>()
                connectionHeaderList.add(StompHeader("Authorization", tkn))
                connectionHeaderList.add(StompHeader("roomId", roomId.toString()))

                stomp.connect(connectionHeaderList)

                // 채팅방 구독
                val topicHeaderList = arrayListOf<StompHeader>()
                topicHeaderList.add(StompHeader("Authorization", tkn))
                stomp.topic("/subscribe/public/$roomId", topicHeaderList).subscribe { topicMessage ->
                    Log.d(TAG, topicMessage.getPayload())
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
        val payload = JSONObject().apply {
            put("roomId", roomId)
            put("content", message)
            put("senderProfilePhoto", userInfo.photo)
        }

        val senderHeaderList = arrayListOf<StompHeader>()
        senderHeaderList.add(StompHeader("Authorization", tkn))
        senderHeaderList.add(StompHeader("destination", "/publish/chat/message"))

        stomp.send(
            StompMessage(
                StompCommand.SEND,
                senderHeaderList,
                payload.toString()
            )
        ).subscribe()

        intent { copy( message = "") }
    }

    fun cancelStomp() {
        try {
            stompConnection.dispose()
            topic.dispose()
        }catch (e: Exception) {
            Log.d(TAG, "cancelStomp: ${e.message}")
        }
    }


}