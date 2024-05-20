package com.sixkids.teacher.board.main

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.LoadSelectedOrganizationNameUseCase
import com.sixkids.teacher.board.chatting.ChattingSideEffect
import com.sixkids.teacher.board.chatting.ChattingState
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardMainViewModel @Inject constructor(
    private val loadOrganizationNameUseCase: LoadSelectedOrganizationNameUseCase
): BaseViewModel<BoardMainState, BoardMainEffect>(BoardMainState()){
    fun init(){
        viewModelScope.launch {
            loadOrganizationNameUseCase().onSuccess {
                intent{ copy(classString = it) }
            }.onFailure {
                intent { copy(classString = "")}
            }
        }

    }
}