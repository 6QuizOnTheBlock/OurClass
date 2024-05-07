package com.sixkids.teacher.challenge.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.challenge.GetChallengeSimpleUseCase
import com.sixkids.teacher.challenge.navigation.ChallengeRoute
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getChallengeSimpleUseCase: GetChallengeSimpleUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ResultState, ResultEffect>(ResultState()) {

    private val challengeId = savedStateHandle.get<Int>(ChallengeRoute.CHALLENGE_ID_NAME)!!
    fun getChallengeInfo() {
        viewModelScope.launch {
            getChallengeSimpleUseCase(challengeId)
                .onSuccess {
                    intent {
                        copy(challenge = it)
                    }
                }
                .onFailure {
                    postSideEffect(ResultEffect.HandleException(it) {
                        getChallengeInfo()
                    })
                }
        }
    }

    fun showResultDialog() {
        postSideEffect(ResultEffect.ShowResultDialog)
    }

}
