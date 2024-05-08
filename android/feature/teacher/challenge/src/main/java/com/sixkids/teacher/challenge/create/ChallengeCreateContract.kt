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
    val organizationId: Int = 0,
) : UiState

sealed interface ChallengeCreateEffect : SideEffect {
    data object NavigateUp : ChallengeCreateEffect
    data class NavigateResult(val challengeId: Long, val title: String) : ChallengeCreateEffect
    data class ShowSnackbar(val snackbarToken: SnackbarToken) : ChallengeCreateEffect

    data class HandleException(val throwable: Throwable, val retry: () -> Unit) :
        ChallengeCreateEffect
}

enum class ChallengeCreateStep {
    INFO,
    GROUP_TYPE,
    MATCHING_TYPE,
    CREATE,
    RESULT,
}
