package com.sixkids.teacher.challenge.detail

import com.sixkids.model.ChallengeDetail
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ChallengeDetailState(
    val isLoading: Boolean = false,
    val challengeDetail: ChallengeDetail = ChallengeDetail(),
) : UiState

sealed interface ChallengeDetailSideEffect : SideEffect {
}
