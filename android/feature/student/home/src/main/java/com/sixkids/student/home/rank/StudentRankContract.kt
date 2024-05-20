package com.sixkids.student.home.rank

import com.sixkids.model.MemberRankItem
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface StudentRankEffect : SideEffect {
    data class onShowSnackBar(val message: String): StudentRankEffect
}

data class StudentRankState(
    val isLoading: Boolean = false,
    val classString: String = "",
    val rankList: List<MemberRankItem> = emptyList(),
): UiState
