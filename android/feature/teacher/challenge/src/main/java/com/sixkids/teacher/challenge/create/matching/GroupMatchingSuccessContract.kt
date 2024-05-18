package com.sixkids.teacher.challenge.create.matching

import com.sixkids.model.ChallengeGroup
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState


data class GroupMatchingSuccessState(
    val isLoading: Boolean = false,
    val groupList: List<ChallengeGroup> = emptyList()
) : UiState

sealed interface GroupMatchingSuccessEffect : SideEffect {
    data class ShowSnackbar(val message: String) : GroupMatchingSuccessEffect
}
