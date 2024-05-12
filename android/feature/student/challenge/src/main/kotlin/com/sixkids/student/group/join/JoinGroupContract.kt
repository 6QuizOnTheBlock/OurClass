package com.sixkids.student.group.join

import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState


data class JoinGroupState(
    val isLoading: Boolean = false,
    val member: MemberSimple = MemberSimple()
) : UiState

sealed interface JoinGroupEffect : SideEffect {
    data class HandleException(val it: Throwable, val retryAction: () -> Unit) : JoinGroupEffect
}
