package com.sixkids.teacher.challenge.create

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.teacher.challenge.create.grouptype.GroupType
import com.sixkids.teacher.challenge.create.grouptype.GroupTypeRoute
import com.sixkids.teacher.challenge.create.info.InfoContentRoute
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle
import java.time.LocalDateTime


@Composable
fun ChallengeCreateRoute(
    viewModel: ChallengeCreateViewModel = hiltViewModel(),
    onNavigateResult: (Int, String) -> Unit,
    onNavigateUp: () -> Unit,
    onHandleException: (Throwable, () -> Unit) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is ChallengeCreateEffect.ShowSnackbar -> onShowSnackbar(it.snackbarToken)
            is ChallengeCreateEffect.NavigateResult -> onNavigateResult(it.challengeId, it.title)
            ChallengeCreateEffect.NavigateUp -> onNavigateUp()
            is ChallengeCreateEffect.HandleException -> onHandleException(it.throwable, it.retry)
        }
    }

    ChallengeCreateScreen(
        uiState = uiState,
        updateTitle = viewModel::updateTitle,
        updateContent = viewModel::updateContent,
        updateStartTime = viewModel::updateStartTime,
        updateEndTime = viewModel::updateEndTime,
        updatePoint = viewModel::updatePoint,
        updateCount = viewModel::updateCount,
        updateGroupType = viewModel::updateGroupType,
        onMoveNextStep = viewModel::moveNextStep,
        onMovePrevStep = viewModel::movePrevStep,
        onShowSnackbar = viewModel::onShowSnackbar,
        createChallenge = viewModel::createChallenge,
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
    updateCount: (String) -> Unit = {},
    updateGroupType: (GroupType) -> Unit = {},
    onMoveNextStep: () -> Unit = {},
    onMovePrevStep: () -> Unit = {},
    createChallenge: () -> Unit = {},
) {

    BackHandler {
        onMovePrevStep()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
    ) {
        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = uiState.step,
            label = "ChallengeCreateScreen",
        ) { targetState ->
            when (targetState) {
                ChallengeCreateStep.INFO -> InfoContentRoute(
                    updateTitle = updateTitle,
                    updateContent = updateContent,
                    updateStartTime = updateStartTime,
                    updateEndTime = updateEndTime,
                    updatePoint = updatePoint,
                    onShowSnackbar = onShowSnackbar,
                    moveNextStep = onMoveNextStep,
                )

                ChallengeCreateStep.GROUP_TYPE -> GroupTypeRoute(
                    updateMinCount = updateCount,
                    updateGroupType = updateGroupType,
//                    moveNextStep = onMoveNextStep,
                    moveNextStep = {
                        onShowSnackbar(SnackbarToken("그룹 지정 로직은 미구현 입니다."))
                    },
                    createChallenge = createChallenge,
                    onShowSnackbar = onShowSnackbar,
                )

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
