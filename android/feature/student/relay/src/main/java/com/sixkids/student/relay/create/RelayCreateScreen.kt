package com.sixkids.student.relay.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.relay.R
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun RelayCreateRoute(
    viewModel: RelayCreateViewModel = hiltViewModel(),
    navigateToRelayResult: (Long) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is RelayCreateEffect.NavigateToRelayResult -> navigateToRelayResult(sideEffect.newRelayId)
            is RelayCreateEffect.OnShowSnackBar -> onShowSnackBar(sideEffect.tkn)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    RelayCreateScreen(
        uiState = uiState,
        onNewRelayClick = viewModel::newRelayClick,
        onBackClick = onBackClick,
        onUpdateQuestion = viewModel::updateQuestion,
    )

}

@Composable
fun RelayCreateScreen(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    uiState: RelayCreateState = RelayCreateState(),
    onNewRelayClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onUpdateQuestion: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            UlbanTopSection(stringResource(id = R.string.relay_create_topsection), onBackClick)

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = stringResource(R.string.relay_create_question),
                style = UlbanTypography.bodyLarge,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
            UlbanUnderLineTextField(
                text = uiState.question,
                hint = stringResource(R.string.relay_create_question_hint),
                onTextChange = onUpdateQuestion,
                onIconClick = {
                    onUpdateQuestion("")
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            UlbanFilledButton(
                text = stringResource(R.string.relay_create_create),
                onClick = { onNewRelayClick() },
                modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RelayCreateScreenPreview() {
    UlbanTheme {
        RelayCreateScreen()
    }
}