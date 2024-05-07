package com.sixkids.teacher.challenge.result

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.sixkids.model.Challenge
import com.sixkids.teacher.challenge.navigation.ChallengeRoute
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
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

    fun getChallengeInfo() {
//        val challenge = ChallengeRepository.getChallenge(challengeId)
        intent {
            copy(
                challenge = Challenge(
                    title = "4월 22일 깜짝 미션",
                    content = "문화의 날을 맞아 우리반 친구들 3명 이상 모여 영화를 관람해봐요~",
                    reword = 1000,
                    startTime = LocalDateTime.of(2024, 4, 22, 4, 30, 0),
                    endTime = LocalDateTime.of(2024, 4, 22, 8, 30, 0)
                )
            )
        }
    }

    fun showResultDialog() {
        postSideEffect(ResultEffect.ShowResultDialog)
    }

}
