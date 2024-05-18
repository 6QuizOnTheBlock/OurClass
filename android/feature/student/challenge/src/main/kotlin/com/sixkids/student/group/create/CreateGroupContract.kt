package com.sixkids.student.group.create

import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class CreateGroupState(
    val isLoading: Boolean = false,
    val isScanning: Boolean = false,
    val foundMembers: List<MemberSimple> = emptyList(),
    val showMembers: Array<MemberSimple?> = Array(5) { null },
    val selectedMembers: List<MemberSimple> = emptyList(),
    val waitingMembers: List<MemberSimple> = emptyList(),
    val groupSize: Int = 0,
    val leader: MemberSimple = MemberSimple(),
    val roomKey: String = "",
) : UiState


sealed interface CreateGroupEffect : SideEffect {
    data object NavigateToChallengeHistory : CreateGroupEffect
    data class HandleException(
        val throwable: Throwable,
        val retryAction: () -> Unit
    ) : CreateGroupEffect

}
