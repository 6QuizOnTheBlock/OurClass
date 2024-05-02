package com.sixkids.teacher.challenge.create

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.theme.UlbanTheme
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
    updatePoint: (String) -> Unit = {},
    onShowSnackbar: (SnackbarToken) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
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
                )

                ChallengeCreateStep.GROUP_TYPE -> TODO()
                ChallengeCreateStep.MATCHING_TYPE -> TODO()
                ChallengeCreateStep.CREATE -> TODO()
                ChallengeCreateStep.RESULT -> TODO()
            }
        }

        UlbanFilledButton(
            text = "다음",
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
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
