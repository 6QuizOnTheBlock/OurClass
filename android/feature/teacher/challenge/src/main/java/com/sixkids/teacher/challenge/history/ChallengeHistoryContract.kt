package com.sixkids.teacher.challenge.history

import com.sixkids.model.RunningChallenge
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ChallengeHistoryState(
    val isLoading: Boolean = false,
    val runningChallenge: RunningChallenge? = null,
    val totalChallengeCount: Int = 0,
) : UiState

sealed interface ChallengeHistoryEffect : SideEffect {
    data class NavigateToChallengeDetail(val challengeId: Long, val groupId: Long? = null) : ChallengeHistoryEffect
    data object NavigateToCreateChallenge : ChallengeHistoryEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ChallengeHistoryEffect
}
