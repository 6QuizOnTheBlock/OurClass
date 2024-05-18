package com.sixkids.teacher.home.rank

import com.sixkids.model.MemberRankItem
import com.sixkids.ui.base.UiState

data class RankState(
    val isLoading: Boolean = false,
    val rankList: List<MemberRankItem> = emptyList(),
): UiState
