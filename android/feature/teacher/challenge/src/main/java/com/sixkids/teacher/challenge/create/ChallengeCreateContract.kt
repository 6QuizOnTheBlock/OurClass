package com.sixkids.teacher.challenge.create

import androidx.annotation.StringRes
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ChallengeCreateUiState(
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
    @StringRes val buttonText: Int? = null,
    val step: ChallengeCreateStep = ChallengeCreateStep.INFO,
) : UiState

sealed interface ChallengeCreateEffect : SideEffect {
    data object NavigateUp : ChallengeCreateEffect
    data class ShowSnackbar(val snackbarToken: SnackbarToken) : ChallengeCreateEffect
}

enum class ChallengeCreateStep {
    INFO,
    GROUP_TYPE,
    MATCHING_TYPE,
    CREATE,
    RESULT,
}
