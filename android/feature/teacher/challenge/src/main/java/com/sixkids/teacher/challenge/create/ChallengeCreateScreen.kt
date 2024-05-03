package com.sixkids.teacher.challenge.create

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.teacher.challenge.create.info.InfoContentRoute
import com.sixkids.teacher.challenge.create.info.InfoViewModel
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import java.time.LocalDateTime


@Composable
fun ChallengeCreateRoute(
    viewModel: ChallengeCreateViewModel = hiltViewModel(),
    infoViewModel: InfoViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val infoUiState = infoViewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle {
    }


    ChallengeCreateScreen(
        uiState = uiState,
        onMoveNextStep = viewModel::moveNextStep,
    )

}

@Composable
fun ChallengeCreateScreen(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    uiState: ChallengeCreateUiState,
    updateTitle: (String) -> Unit = {},
    updateContent: (String) -> Unit = {},
    updateStartTime: (LocalDateTime) -> Unit = {},
    updateEndTime: (LocalDateTime) -> Unit = {},
    updatePoint: (String) -> Unit = {},
    onShowSnackbar: (SnackbarToken) -> Unit = {},
    onMoveNextStep: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize().
        padding(paddingValues),
    ) {
        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = uiState.step,
            label = "ChallengeCreateScreen",
        ) { targetState ->
            when (targetState) {
                ChallengeCreateStep.INFO -> InfoContentRoute(
                    updateTitle = updateTitle,
                    updateContent = {},
                    updateStartTime = {},
                    updateEndTime = {},
                    updatePoint = updatePoint,
                    onShowSnackbar = {},
                    moveNextStep = onMoveNextStep,
                )

                ChallengeCreateStep.GROUP_TYPE -> TODO()
                ChallengeCreateStep.MATCHING_TYPE -> TODO()
                ChallengeCreateStep.CREATE -> TODO()
                ChallengeCreateStep.RESULT -> TODO()
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun ChallengeCreateScreenPreview() {
    UlbanTheme {
        ChallengeCreateScreen(
            uiState = ChallengeCreateUiState(),
        )
    }
}
