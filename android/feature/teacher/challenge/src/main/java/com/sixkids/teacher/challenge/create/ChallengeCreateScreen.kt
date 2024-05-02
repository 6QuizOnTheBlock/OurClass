package com.sixkids.teacher.challenge.create

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.teacher.challenge.create.info.InfoContentRoute
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import java.time.LocalDateTime


@Composable
fun ChallengeCreateRoute(
    viewModel: ChallengeCreateViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle {
    }


    ChallengeCreateScreen(
        uiState = uiState,
        )

}

@Composable
fun ChallengeCreateScreen(
    uiState: ChallengeCreateUiState,
    updateTitle: (String) -> Unit = {},
    updateContent: (String) -> Unit = {},
    updateStartTime: (LocalDateTime) -> Unit = {},
    updateEndTime: (LocalDateTime) -> Unit = {},
    updatePoint: (Int) -> Unit = {},
    onShowSnackbar: (SnackbarToken) -> Unit = {},
) {
    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        targetState = uiState.step,
        label = "ChallengeCreateScreen",
    ) { targetState ->
        when (targetState) {
            ChallengeCreateStep.INFO -> InfoContentRoute(
                updateTitle = updateTitle,
                updateContent = {},
                updateStartTime = {},
                updateEndTime = {},
                updatePoint = {},
                onShowSnackbar = {},
            )

            ChallengeCreateStep.GROUP_TYPE -> TODO()
            ChallengeCreateStep.MATCHING_TYPE -> TODO()
            ChallengeCreateStep.CREATE -> TODO()
            ChallengeCreateStep.RESULT -> TODO()
        }
    }
}
