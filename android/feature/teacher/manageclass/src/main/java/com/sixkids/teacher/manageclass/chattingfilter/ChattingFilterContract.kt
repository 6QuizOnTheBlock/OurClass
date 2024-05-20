package com.sixkids.teacher.manageclass.chattingfilter

import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

sealed interface ChattingFilterEffect : SideEffect {
    data object refreshChattingFilterWords : ChattingFilterEffect
    data class onShowSnackBar(val message: String) : ChattingFilterEffect
}

data class ChattingFilterState(
    val isLoading: Boolean = false,
    val isShowDialog: Boolean = false,
    val classString: String = "",
    val dialogText: String = "",
) : UiState