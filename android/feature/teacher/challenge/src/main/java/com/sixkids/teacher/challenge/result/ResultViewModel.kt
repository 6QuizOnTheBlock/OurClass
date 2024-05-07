package com.sixkids.teacher.challenge.result

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.sixkids.teacher.challenge.navigation.ChallengeRoute
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "D107"

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ResultState, ResultEffect>(ResultState()) {

    private val challengeId = savedStateHandle.get<Int>(ChallengeRoute.CHALLENGE_ID_NAME)!!

    init {
        Log.d(TAG, " challengeId: $challengeId ")
    }

    fun showResultDialog() {
        postSideEffect(ResultEffect.ShowResultDialog)
    }

}
