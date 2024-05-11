package com.sixkids.student.group.join

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState


data class JoinGroupState(
    val isLoading: Boolean = false,
) : UiState

sealed interface
JoinGroupEffect : SideEffect {
}
