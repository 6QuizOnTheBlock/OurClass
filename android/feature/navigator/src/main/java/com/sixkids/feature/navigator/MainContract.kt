package com.sixkids.feature.navigator

import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class MainState(
    val snackbarToken: SnackbarToken = SnackbarToken(),
    val snackbarVisible: Boolean = false,
) : UiState

sealed interface MainSideEffect : SideEffect {
    data object ShowSnackbar : MainSideEffect
}
