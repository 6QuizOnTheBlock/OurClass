package com.sixkids.student.group.create.matched

import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class MatchedCreateGroupState(
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


sealed interface MatchedCreateGroupEffect : SideEffect {
    data object NavigateToChallengeHistory :
        MatchedCreateGroupEffect
    data class HandleException(
        val throwable: Throwable,
        val retryAction: () -> Unit
    ) : MatchedCreateGroupEffect

}
