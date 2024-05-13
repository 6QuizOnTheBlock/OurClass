package com.sixkids.student.relay.pass.answer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.button.UlbanFilledButton
import com.sixkids.designsystem.component.screen.UlbanTopSection
import com.sixkids.designsystem.component.textfield.UlbanUnderLineTextField
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.relay.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.UiState
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun RelayAnswerRoute(
    viewModel: RelayAnswerViewModel = hiltViewModel(),
    navigateToTaggingSenderRelay: (Long) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is RelayAnswerEffect.NavigateToTaggingSenderRelay -> navigateToTaggingSenderRelay(sideEffect.relayId)
            is RelayAnswerEffect.OnShowSnackBar -> onShowSnackBar(sideEffect.tkn)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    RelayAnswerScreen(
        uiState = uiState,
        onNextClick = viewModel::nextClick,
        onBackClick = onBackClick,
        onUpdateNextQuestion = viewModel::updateNextQuestion
    )
}

@Composable
fun RelayAnswerScreen(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    uiState: RelayAnswerState = RelayAnswerState(),
    onNextClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onUpdateNextQuestion: (String) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start,
        ) {
            UlbanTopSection(stringResource(R.string.relay_answer_title), onBackClick)

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = stringResource(R.string.relay_create_question),
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
            UlbanUnderLineTextField(
                text = uiState.nextQuestion,
                hint = stringResource(R.string.relay_create_question_hint),
                onTextChange = onUpdateNextQuestion,
                onIconClick = {
                    onUpdateNextQuestion("")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.relay_answer_pre_question),
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )

            Column {
                Text(text = uiState.preQuestion, style = UlbanTypography.bodyMedium)

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Blue,
                    thickness = 2.dp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            UlbanFilledButton(
                text = stringResource(R.string.button_next),
                onClick = { onNextClick() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RelayAnswerScreenPreview() {
    UlbanTheme {
        RelayAnswerScreen()
    }
}