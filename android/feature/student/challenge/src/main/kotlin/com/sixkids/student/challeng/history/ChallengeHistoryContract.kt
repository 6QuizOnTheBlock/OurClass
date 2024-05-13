package com.sixkids.student.challeng.history

import androidx.paging.PagingData
import com.sixkids.model.Challenge
import com.sixkids.model.GroupType
import com.sixkids.model.RunningChallenge
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState
import kotlinx.coroutines.flow.Flow

data class ChallengeHistoryState(
    val isLoading: Boolean = false,
    val runningChallenge: RunningChallenge? = null,
    val totalChallengeCount: Int = 0,
    val organizationId: Int = 0,
    val challengeHistory: Flow<PagingData<Challenge>>? = null
) : UiState

sealed interface ChallengeHistoryEffect : SideEffect {
    data class NavigateToChallengeDetail(val challengeId: Long, val groupId: Long? = null) : ChallengeHistoryEffect
    data class NavigateToCreateGroup(val challengeId: Long, val groupType: GroupType) : ChallengeHistoryEffect
    data class NavigateToJoinGroup(val challengeId: Long) : ChallengeHistoryEffect
    data object ShowGroupDialog : ChallengeHistoryEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ChallengeHistoryEffect
}
