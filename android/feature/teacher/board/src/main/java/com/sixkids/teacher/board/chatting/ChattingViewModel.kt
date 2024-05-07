package com.sixkids.teacher.board.chatting

import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(

) : BaseViewModel<ChattingState, ChattingSideEffect>(ChattingState()){

    fun updateMessage(message: String){
        intent { copy(message = message) }
    }

    fun sendMessage(message: String){

    }
}