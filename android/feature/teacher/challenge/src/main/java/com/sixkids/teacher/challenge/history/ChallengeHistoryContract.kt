package com.sixkids.teacher.challenge.history

import com.sixkids.model.ActiveChallenge
import com.sixkids.model.Challenge
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ChallengeHistoryState(
    val isLoading: Boolean = false,
    val challengeHistory: List<Challenge> = emptyList(),
    val currentChallenge: ActiveChallenge? = null,
    val totalChallengeCount: Int = 0,
) : UiState

sealed interface ChallengeHistoryEffect : SideEffect {
    data object NavigateToChallengeDetail : ChallengeHistoryEffect
    data object NavigateToCreateChallenge : ChallengeHistoryEffect
}
