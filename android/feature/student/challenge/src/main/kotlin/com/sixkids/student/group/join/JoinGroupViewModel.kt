package com.sixkids.student.group.join

import androidx.lifecycle.viewModelScope
import com.sixkids.core.bluetooth.BluetoothServer
import com.sixkids.domain.usecase.sse.SseConnectUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val bluetoothServer: BluetoothServer,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val sseConnectUseCase: SseConnectUseCase
) : BaseViewModel<JoinGroupState, JoinGroupEffect>(JoinGroupState()) {

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

}
