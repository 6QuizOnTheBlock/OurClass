package com.sixkids.student.group.create

import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class CreateGroupState(
    val isLoading: Boolean = false,
    val isScanning: Boolean = false,
    val foundMembers: List<MemberSimple> = emptyList(),
    val selectedMembers: List<MemberSimple> = emptyList(),
) : UiState


sealed interface CreateGroupEffect : SideEffect {
    data object FriendScanStart : CreateGroupEffect
    data object FriendScanStop : CreateGroupEffect

}
