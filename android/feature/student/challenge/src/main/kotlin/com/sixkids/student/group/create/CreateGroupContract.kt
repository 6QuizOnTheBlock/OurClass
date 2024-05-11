package com.sixkids.student.group.create

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class CreateGroupState(
    val isLoading: Boolean = false,
): UiState


sealed interface CreateGroupEffect: SideEffect {
}
