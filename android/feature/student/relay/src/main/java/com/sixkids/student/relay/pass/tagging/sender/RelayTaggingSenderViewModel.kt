package com.sixkids.student.relay.pass.tagging.sender

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.relay.SendRelayUseCase
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.BadRequestException
import com.sixkids.model.NotFoundException
import com.sixkids.model.RelaySend
import com.sixkids.student.relay.navigation.RelayRoute
import com.sixkids.student.relay.pass.tagging.RelayNfc
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"

@HiltViewModel
class RelayTaggingSenderViewModel @Inject constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val sendRelayUseCase: SendRelayUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RelayTaggingSenderState, RelayTaggingSenderEffect>(RelayTaggingSenderState()) {
    private val relayId = savedStateHandle.get<Long>(RelayRoute.RELAY_ID_NAME)
    private val question = savedStateHandle.get<String>(RelayRoute.RELAY_QUESTION_NAME)

    fun init() {
        viewModelScope.launch {
            loadUserInfoUseCase().onSuccess {
                intent { copy(relayNfc = RelayNfc(relayId ?: -1, it.id.toLong(), question ?: "")) }
            }.onFailure {
                Log.d(TAG, "init: ${it.message}")
            }
        }
    }

    fun checkRelaySent() {
        viewModelScope.launch {
            sendRelayUseCase(relayId?.toInt() ?: -1)
                .onSuccess {
                    intent { copy(relaySend = it) }
                }.onFailure {
                    when(it){
                        is NotFoundException -> {
                            intent { copy(relaySend = RelaySend("", "첫번째 주자입니다!")) }
                        }
                        is BadRequestException -> {
                            postSideEffect(
                                RelayTaggingSenderEffect.OnShowSnackBar(
                                    SnackbarToken("이어 달리기가 전달되지 않았어요. 다시 시도해 보세요")
                                )
                            )
                        }
                        else -> {
                            postSideEffect(
                                RelayTaggingSenderEffect.OnShowSnackBar(
                                    SnackbarToken(it.message ?: "이어 달리기 전달에 실패했어요")
                                )
                            )
                        }
                    }
                }
        }
    }
}