package com.sixkids.teacher.home.main

import com.sixkids.designsystem.theme.component.card.RunningRelayState
import com.sixkids.designsystem.theme.component.card.RunningTogetherState

sealed interface HomeMainEffect{
    data object NavigateToRanking : HomeMainEffect
}

data class HomeMainState(
    val isLoading: Boolean = false,
    val teacherName: String = "",
    val runningTogetherState: RunningTogetherState? = null,
    val runningRelayState: RunningRelayState? = null,
)