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
import com.sixkids.ui.extension.collectWithLifecycle

@Composable
fun ResultRoute(
    viewModel: ResultViewModel = hiltViewModel(),
    onShowDialog : () -> Unit = {},
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            ResultEffect.ShowResultDialog -> TODO()
        }
    }
    ResultScreen()
}


@Composable
fun ResultScreen(
    paddingValues: PaddingValues = PaddingValues(32.dp)
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
            title = "챌린지",
            subTitle = "챌린지 설명",
            onClick = {}
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
