package com.sixkids.teacher.challenge.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sixkids.designsystem.component.card.UlbanMissionCard
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.teacher.challenge.R
import com.sixkids.teacher.challenge.result.component.ChallengeDialog
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun ResultRoute(
    viewModel: ResultViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    var showDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getChallengeInfo()
    }

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            ResultEffect.ShowResultDialog -> {
                showDialog = true
            }
        }
    }

    ResultScreen(
        uiState = uiState,
        showDialog = viewModel::showResultDialog,
    )

    with(uiState.challenge) {
        if (showDialog) {
            ChallengeDialog(
                title = title,
                content = content,
                startTime = startTime,
                endTime = endTime,
                point = reword,
                onConfirmClick = {
                    showDialog = false
                }
            )
        }
    }
}


@Composable
fun ResultScreen(
    paddingValues: PaddingValues = PaddingValues(32.dp),
    uiState: ResultState = ResultState(),
    showDialog: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.new_challenge_created),
            style = UlbanTypography.titleSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))
        UlbanMissionCard(
            title = uiState.challenge.title,
            subTitle = stringResource(R.string.detail_info),
            onClick = showDialog
        )
        Image(
            modifier = Modifier.size(160.dp),
            painter = painterResource(id = R.drawable.challenge_created_success),
            contentDescription = "challenge success"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewResultContent() {
    UlbanTheme {
        ResultScreen()
    }
}
