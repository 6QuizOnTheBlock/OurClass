package com.sixkids.teacher.challenge.detail

import com.sixkids.model.ChallengeDetail
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction2

data class ChallengeDetailState(
    val isLoading: Boolean = false,
    val challengeDetail: ChallengeDetail = ChallengeDetail(),
) : UiState

sealed interface ChallengeDetailSideEffect : SideEffect {
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) :
        ChallengeDetailSideEffect
}
