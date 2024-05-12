package com.sixkids.student.home.main

import com.sixkids.model.MemberSimpleWithScore
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface StudentHomeMainEffect: SideEffect {
    data object navigateToAnnounce: StudentHomeMainEffect
    data object navigateToTagHello : StudentHomeMainEffect
    data object navigateToRank : StudentHomeMainEffect
    data object navigateToChatting : StudentHomeMainEffect
    data class onShowSnackBar(val message: String) : StudentHomeMainEffect
}

data class StudentHomeMainState(
    val studentName: String = "",
    val studentImageUrl: String = "",
    val studentClass: String = "",
    val studentExp: Int = 0,
    val bestFriendList: List<MemberSimpleWithScore> = emptyList()
): UiState