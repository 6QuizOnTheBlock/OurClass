package com.sixkids.teacher.home.main

import com.sixkids.designsystem.theme.component.card.RunningRelayState
import com.sixkids.designsystem.theme.component.card.RunningTogetherState
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface HomeMainEffect: SideEffect{
    data object NavigateToRanking : HomeMainEffect
}

data class HomeMainState(
    val isLoading: Boolean = false,
    val classString: String = "",
    val teacherName: String = "",
    val teacherImageUrl: String = "",
): UiState