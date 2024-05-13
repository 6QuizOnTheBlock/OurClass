package com.sixkids.student.relay.pass.answer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.relay.GetRelayQuestionUseCase
import com.sixkids.model.NotFoundException
import com.sixkids.student.relay.navigation.RelayRoute
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelayAnswerViewModel @Inject constructor(
    private val getRelayQuestionUseCase: GetRelayQuestionUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel<RelayAnswerState, RelayAnswerEffect>(RelayAnswerState()) {
    private val relayId = savedStateHandle.get<Long>(RelayRoute.RELAY_ID_NAME)

    fun init() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getRelayQuestionUseCase(relayId!!)
                .onSuccess { question ->
                    intent { copy(preQuestion = question) }
                }
                .onFailure { exception ->
                    when(exception){
                        is NotFoundException -> intent { copy(preQuestion = "첫번째 주자입니다!")}
                        else -> postSideEffect(RelayAnswerEffect.OnShowSnackBar(SnackbarToken(exception.message ?: "질문을 받아오는데 실패했습니다")))
                    }
                }
        }
    }

    fun updateNextQuestion(question: String) {
        intent { copy(nextQuestion = question) }
    }

    fun nextClick() {

    }
}