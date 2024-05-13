package com.sixkids.student.relay.pass.answer

import androidx.lifecycle.viewModelScope
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelayAnswerViewModel @Inject constructor(

): BaseViewModel<RelayAnswerState, RelayAnswerEffect>(RelayAnswerState()) {

    fun init() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            //내가 받은 질문 불러오기
            // 첫 주자면 404 대응
        }
    }

    fun updateNextQuestion(question: String) {
        intent { copy(nextQuestion = question) }
    }

    fun nextClick() {

    }
}