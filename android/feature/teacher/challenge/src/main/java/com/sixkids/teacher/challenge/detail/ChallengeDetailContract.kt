package com.sixkids.teacher.challenge.detail

import com.sixkids.model.ActiveChallenge
import com.sixkids.model.Challenge
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ChallengeDetailState(
    val isLoading: Boolean = false,
    val challengeHistory: List<Challenge> = emptyList(),
    val currentChallenge: ActiveChallenge? = null,
    val totalChallengeCount: Int = 0,
) : UiState

sealed interface ChallengeDetailContract : SideEffect {
}
