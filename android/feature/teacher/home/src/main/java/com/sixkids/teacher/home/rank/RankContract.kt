package com.sixkids.teacher.home.rank

import com.sixkids.model.MemberRankItem
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface RankEffect : SideEffect {
    data class onShowSnackBar(val message: String): RankEffect
}

data class RankState(
    val isLoading: Boolean = false,
    val classString: String = "",
    val rankList: List<MemberRankItem> = emptyList(),
): UiState
