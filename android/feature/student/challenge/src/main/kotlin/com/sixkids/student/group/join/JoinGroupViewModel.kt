package com.sixkids.student.group.join

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.core.bluetooth.BluetoothServer
import com.sixkids.domain.sse.SseEventListener
import com.sixkids.domain.usecase.sse.SseConnectUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.MemberSimple
import com.sixkids.model.SseEventType
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val bluetoothServer: BluetoothServer,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val sseConnectUseCase: SseConnectUseCase,
) : BaseViewModel<JoinGroupState, JoinGroupEffect>(JoinGroupState()), SseEventListener {


    init {
        sseConnectUseCase.setEventListener(this)
    }

    fun connectSse() = viewModelScope.launch {
        sseConnectUseCase()
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

    override fun onEventReceived(event: SseEventType, data: String) {
        when (event) {
            SseEventType.SSE_CONNECT -> Log.d(TAG, "onEventReceived: SSE 연결 성공")
            SseEventType.INVITE_REQUEST -> Log.d(TAG, "onEventReceived: 초대 요청")
            SseEventType.INVITE_RESPONSE -> Log.d(TAG, "onEventReceived: 초대 응답")
            SseEventType.KICK_MEMBER -> Log.d(TAG, "onEventReceived: 멤버 강퇴")
            SseEventType.CREATE_GROUP -> Log.d(TAG, "onEventReceived: 그룹 생성")
        }
    }

    override fun onError(error: Throwable) {
        postSideEffect(JoinGroupEffect.HandleException(error, ::connectSse))
    }

}
