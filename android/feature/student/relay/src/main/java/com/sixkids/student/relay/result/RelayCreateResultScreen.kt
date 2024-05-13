package com.sixkids.student.relay.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.UlbanTheme
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.student.relay.R
import com.sixkids.ui.extension.collectWithLifecycle
import com.sixkids.designsystem.R as DesignSystemR

@Composable
fun RelayCreateResultRoute(
    viewModel: RelayCreateResultViewModel = hiltViewModel(),
    navigateToRelayHistory: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit
) {
    viewModel.sideEffect.collectWithLifecycle {
        when (it) {
            is RelayCreateResultEffect.HandleException -> handleException(it.throwable, it.retry)
            RelayCreateResultEffect.NavigateToRelayHistory -> navigateToRelayHistory()
        }
    }

    RelayCreateResultScreen(
        onClickConfirm = viewModel::navigateToChallengeHistory
    )
}


@Composable
fun RelayCreateResultScreen(
    paddingValues: PaddingValues = PaddingValues(32.dp),
    onClickConfirm: () -> Unit = {}
) {
    BackHandler {
        onClickConfirm()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.create_relay_success),
            style = UlbanTypography.titleSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))
        UlbanMissionCard(
            imgRes = DesignSystemR.drawable.relay,
            title = "친구에게 전달해 봐요!",
            backGroundColor = Orange
        )
        Image(
            modifier = Modifier
                .size(160.dp)
                .clickable {
                    onClickConfirm()
                },
            painter = painterResource(id = R.drawable.relay_created_success),
            contentDescription = "challenge success"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewResultContent() {
    UlbanTheme {
        RelayCreateResultScreen()
    }
}
