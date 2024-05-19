package com.sixkids.teacher.challenge.create.matching

import com.sixkids.model.ChallengeGroup
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState


data class GroupMatchingSuccessState(
    val isLoading: Boolean = false,
    val groupList: List<ChallengeGroup> = emptyList(),
    val orgId: Long = 0L,
    val minCount: Int = 0,
    val matchingType: MatchingType = MatchingType.FRIENDLY,
    val members: List<Long> = emptyList()
) : UiState

sealed interface GroupMatchingSuccessEffect : SideEffect {
    data class ShowSnackbar(val message: String) : GroupMatchingSuccessEffect
}

data class MatchingSource(
    val orgId: Long = 0L,
    val minCount: Int = 0,
    val matchingType: MatchingType = MatchingType.FRIENDLY,
    val members: List<Long> = emptyList()
)
