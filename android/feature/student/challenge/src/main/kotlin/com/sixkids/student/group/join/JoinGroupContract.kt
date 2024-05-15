package com.sixkids.student.group.join

import com.sixkids.model.MemberSimple
import com.sixkids.student.group.component.MemberIconItem
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState


data class JoinGroupState(
    val isLoading: Boolean = false,
    val leader: MemberIconItem = MemberIconItem(),
    val member: MemberSimple = MemberSimple(),
    val roomKey: String = "",
    val isJoinedGroup: Boolean = false,
) : UiState

sealed interface JoinGroupEffect : SideEffect {
    data class HandleException(val it: Throwable, val retryAction: () -> Unit) : JoinGroupEffect
    data object ReceiveInviteRequest : JoinGroupEffect
    data object CloseInviteDialog : JoinGroupEffect
}
