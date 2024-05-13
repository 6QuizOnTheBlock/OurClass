package com.sixkids.student.relay.pass.tagging.sender

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.student.relay.navigation.RelayRoute
import com.sixkids.student.relay.pass.tagging.RelayNfc
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class RelayTaggingSenderViewModel @Inject constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel<RelayTaggingSenderState, RelayTaggingSenderEffect>(RelayTaggingSenderState()){
    private val relayId = savedStateHandle.get<Long>(RelayRoute.RELAY_ID_NAME)
    private val question = savedStateHandle.get<String>(RelayRoute.RELAY_QUESTION_NAME)

    fun init() {
        viewModelScope.launch {
            loadUserInfoUseCase().onSuccess {
                intent { copy(relayNfc = RelayNfc(relayId?:-1, it.id.toLong(), question?:"")) }
            }.onFailure {
                Log.d(TAG, "init: ${it.message}")
            }
        }
    }
}